package cn.cerestech.middleware.sms.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Sets;

import cn.cerestech.framework.core.date.Dates;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.configuration.service.ConfigService;
import cn.cerestech.middleware.location.ip.IP;
import cn.cerestech.middleware.location.mobile.Mobile;
import cn.cerestech.middleware.sms.SmsFireWall;
import cn.cerestech.middleware.sms.dao.SmsDao;
import cn.cerestech.middleware.sms.entity.SmsRecord;
import cn.cerestech.middleware.sms.entity.SmsResult;
import cn.cerestech.middleware.sms.enums.ErrorCodes;
import cn.cerestech.middleware.sms.enums.SmsConfigKeys;
import cn.cerestech.middleware.sms.enums.SmsProviderAuthKey;
import cn.cerestech.middleware.sms.enums.SmsState;
import cn.cerestech.middleware.sms.providers.Provider;
import cn.cerestech.middleware.sms.providers.Sms;
import cn.cerestech.middleware.sms.providers.YunPianProvider;

@Service
public class SmsService {

	@Autowired
	ConfigService configService;

	@Autowired
	SmsDao smsDao;

	private static Provider defaultProvider = new YunPianProvider();

	/**
	 * 是否开启短信发送功能
	 * 
	 * @return
	 */
	public Boolean isSMSEnabled() {
		return configService.query(SmsConfigKeys.SMS_ENABLE).boolValue();
	}

	/**
	 * 返回默认的短信发送器
	 * 
	 * @param to
	 * @return
	 */
	public Provider getDefaultSmsProvider(Mobile to) {
		return defaultProvider;// 默认暂时只有云片
	}

	public Result<SmsRecord> send(Mobile to, String content, IP ip) {
		return send(new Supplier<Sms>() {
			@Override
			public Sms get() {
				return new Sms() {

					@Override
					public Mobile getTo() {
						return to;
					}

					@Override
					public IP getIp() {
						return ip;
					}

					@Override
					public String getContent() {
						return content;
					}
				};
			}
		});

	}

	public Result<SmsRecord> send(Supplier<Sms> supplier) {
		if (isSMSEnabled()) {
			return Result.success();
		}
		Sms sms = supplier.get();
		if (sms == null) {
			return Result.error(ErrorCodes.CONTENT_ILLEGAL);
		}

		SmsRecord record = SmsRecord.from(sms);
		Provider provider = getDefaultSmsProvider(record.getTo());
		record.setProvider(provider.getSmsProvider());
		// 判断是计划发送还是立即发送
		if (record.getPlanTime() != null && record.getPlanTime().after(new Date())) {
			// 以后发送，加入Buffer
			record.setState(SmsState.PLANNING);
		} else {
			// 立即发送

			// 检查是否符合发送条件
			SmsFireWall firewall = provider.getFireWall();
			if (firewall != null) {
				// 校验IP限制
				Integer ipLimit = firewall.sameIpInterval();
				if (ipLimit != null && record.getIp() != null && !record.getIp().isEmpty()) {
					Date before = Dates.now().addMillisecond(-1 * ipLimit).toDate();
					if (smsDao.findUniqueBySendedTimeIsNotNullAndSendTimeGreaterThanAndStateAndIpAddr(before,
							SmsState.SUCCESS, record.getIp().getAddr()) != null) {
						// 在指定时间内有发送成功的短信
						SmsResult ret = new SmsResult();
						ret.setMessage("IP地址[" + record.getIp().getAddr() + "] 在 " + ipLimit + " 毫秒内 重复发送");
						ret.setSuccess(Boolean.FALSE);
						record.setResult(ret);
						record.setState(SmsState.ERROR);
						record.setSendedTime(new Date());
						smsDao.save(record);
						return Result.error(ErrorCodes.IP_BUSY);
					}
				}

				// 校验电话限制
				Integer mobileLimit = firewall.sameMobileInterval();
				if (mobileLimit != null) {
					Date before = Dates.now().addMillisecond(-1 * mobileLimit).toDate();
					if (smsDao.findUniqueBySendedTimeIsNotNullAndSendTimeGreaterThanAndStateAndToNumber(before,
							SmsState.SUCCESS, record.getTo().number()) != null) {
						// 在指定时间内有发送成功的短信
						SmsResult ret = new SmsResult();
						ret.setMessage("电话号码[" + record.getTo().number() + "] 在 " + mobileLimit + " 毫秒内 重复发送");
						ret.setSuccess(Boolean.FALSE);
						record.setResult(ret);
						record.setState(SmsState.ERROR);
						record.setSendedTime(new Date());
						smsDao.save(record);
						return Result.error(ErrorCodes.PHONE_BUSY);
					}
				}

			}
			// 发送短信

			SmsResult ret = provider.send(record.getTo(), record.getContent(), getConfigParams(provider.authKeys()));
			record.setResult(ret);
			record.setState(ret.isSuccess() ? SmsState.SUCCESS : SmsState.ERROR);
			record.setSendedTime(new Date());
		}

		smsDao.save(record);
		return Result.success(record);
	}

	public Result<Collection<SmsRecord>> sendBatch(Supplier<Collection<Sms>> supplier) {
		if (isSMSEnabled()) {
			return Result.success(Lists.newArrayList());
		}
		Collection<Sms> smsList = supplier.get();
		if (smsList == null) {
			return Result.error(ErrorCodes.CONTENT_ILLEGAL);
		} else if (smsList.isEmpty()) {
			return Result.success(Lists.newArrayList());
		}

		List<SmsRecord> smsBuffer = Lists.newArrayList();
		Set<String> ipMap = Sets.newHashSet(), mobileMap = Sets.newHashSet();

		smsList.forEach(sms -> {
			SmsRecord record = SmsRecord.from(sms);
			Provider provider = getDefaultSmsProvider(record.getTo());
			record.setProvider(provider.getSmsProvider());
			// 判断是计划发送还是立即发送
			if (record.getPlanTime() != null && record.getPlanTime().after(new Date())) {
				// 以后发送，加入Buffer
				record.setState(SmsState.PLANNING);
				smsBuffer.add(record);
			} else {
				// 检查是否符合发送条件
				SmsFireWall firewall = provider.getFireWall();
				if (firewall != null) {
					// 校验IP限制
					Integer ipLimit = firewall.sameIpInterval();
					if (ipLimit != null && record.getIp() != null && !record.getIp().isEmpty()) {
						Date before = Dates.now().addMillisecond(-1 * ipLimit).toDate();
						String ip = record.getIp().getAddr();
						// 在当前批次或者数据库中存在的，则视为违反
						if (ipMap.contains(ip)
								|| smsDao.findUniqueBySendedTimeIsNotNullAndSendTimeGreaterThanAndStateAndIpAddr(before,
										SmsState.SUCCESS, ip) != null) {
							// 在指定时间内有发送成功的短信
							SmsResult ret = new SmsResult();
							ret.setMessage("IP地址[" + record.getIp().getAddr() + "] 在 " + ipLimit + " 毫秒内 重复发送");
							ret.setSuccess(Boolean.FALSE);
							record.setResult(ret);
							record.setState(SmsState.ERROR);
							record.setSendedTime(new Date());
							smsBuffer.add(record);
							return;
						}
						ipMap.add(ip);
					}

					// 校验电话限制
					Integer mobileLimit = firewall.sameMobileInterval();
					if (mobileLimit != null) {
						Date before = Dates.now().addMillisecond(-1 * mobileLimit).toDate();
						String mobile = record.getTo().number();
						// 在当前批次或者数据库中存在的，则视为违反
						if (mobileMap.contains(mobile)
								|| smsDao.findUniqueBySendedTimeIsNotNullAndSendTimeGreaterThanAndStateAndToNumber(before,
										SmsState.SUCCESS, record.getTo().number()) != null) {
							// 在指定时间内有发送成功的短信
							SmsResult ret = new SmsResult();
							ret.setMessage("电话号码[" + record.getTo().number() + "] 在 " + mobileLimit + " 毫秒内 重复发送");
							ret.setSuccess(Boolean.FALSE);
							record.setResult(ret);
							record.setState(SmsState.ERROR);
							record.setSendedTime(new Date());
							smsBuffer.add(record);
							return;
						}
						mobileMap.add(mobile);
					}

				}

				// 立即发送
				SmsResult ret = provider.send(record.getTo(), record.getContent(),
						getConfigParams(provider.authKeys()));
				record.setResult(ret);
				record.setState(ret.isSuccess() ? SmsState.SUCCESS : SmsState.ERROR);
				record.setSendedTime(new Date());
				smsBuffer.add(record);
			}
		});

		smsDao.save(smsBuffer);
		return Result.success(smsBuffer);
	}

	private Map<String, Object> getConfigParams(SmsProviderAuthKey[] authkeys) {
		Map<String, Object> param = Maps.newHashMap();
		for (SmsProviderAuthKey key : authkeys) {
			param.put(key.authKey(), configService.query(key).stringValue());
		}
		return param;
	}

}
