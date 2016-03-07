package cn.cerestech.middleware.sms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cn.cerestech.framework.core.Dates;
import cn.cerestech.framework.core.PhoneUtils;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.middleware.sms.entity.SmsRecord;
import cn.cerestech.middleware.sms.entity.SmsSendResult;
import cn.cerestech.middleware.sms.enums.ErrorCodes;
import cn.cerestech.middleware.sms.enums.Sms;
import cn.cerestech.middleware.sms.enums.SmsConfigKeys;
import cn.cerestech.middleware.sms.enums.SmsProviderAuthKey;
import cn.cerestech.middleware.sms.enums.SmsState;
import cn.cerestech.middleware.sms.monitor.SmsMonitor;
import cn.cerestech.middleware.sms.providers.HuoniProvider;
import cn.cerestech.middleware.sms.providers.SmsProvider;
import cn.cerestech.middleware.sms.providers.YunPianProvider;

public class SmsMessageService  {

	private static Set<SmsProvider> providers = Sets.newHashSet(new YunPianProvider(), new HuoniProvider());

	@Autowired
	ConfigService configService;

	@Autowired
	SmsMonitor smsMonitor;

	/**
	 * 是否开启短信发送功能
	 * 
	 * @return
	 */
	public Boolean isSMSEnabled() {
		return configService.query(SmsConfigKeys.SMS_ENABLE).boolValue();
	}

	public Set<SmsProvider> getProviders() {
		return providers;
	}

	public SmsProvider getProvider(String name) {
		return getProviders().stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst()
				.orElse(defaultProvider());
	}

	/**
	 * 设置服务供应商，如果找不到，则返回空
	 * 
	 * @param name
	 * @return
	 */
	public SmsProvider setProvider(String name) {
		SmsProvider smsProvider = getProviders().stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst()
				.orElse(null);
		if (smsProvider == null) {
			return null;
		}
		configService.update(SmsConfigKeys.SMS_DEFAULT_PROVIDER, name);
		return smsProvider;
	}

	public SmsProvider defaultProvider() {
		String name = configService.query(SmsConfigKeys.SMS_DEFAULT_PROVIDER).stringValue();
		for (SmsProvider p : getProviders()) {
			if (p.getName().equalsIgnoreCase(name)) {
				return p;
			}
		}

		return getProviders().stream().findFirst().orElse(new YunPianProvider());
	}

	public void defaultProvider(SmsProvider provider) {
		configService.update(SmsConfigKeys.SMS_DEFAULT_PROVIDER, provider.getName());
	}

	protected void sendInstant(String phone, String text) {

	}

	public Result<SmsRecord> send(SmsRecord sr, Boolean checkFrequency) {
		return sendSms(sr, checkFrequency);
	}

	public Result<SmsRecord> send(SmsRecord sr) {
		return send(sr, Boolean.TRUE);
	}

	private Result<SmsRecord> sendSms(SmsRecord sr, Boolean checkFrequency) {
		sr.setSend_time(new Date());
		if (!isSMSEnabled()) {
			sr.setMessage(ErrorCodes.SMS_NOT_OPEN.desc());
			sr.setState(SmsState.ERROR.key());
			persist(sr);
			return Result.error(ErrorCodes.SMS_NOT_OPEN).setObject(sr);
		}

		// 校验电话号码
		if (!PhoneUtils.isPhone(sr.getPhone())) {
			sr.setMessage(ErrorCodes.PHONE_ILLEGAL.desc());
			sr.setState(SmsState.ERROR.key());
			persist(sr);
			return Result.error(ErrorCodes.PHONE_ILLEGAL).setObject(sr);
		}

		// 校验模板内容
		if (Strings.isNullOrEmpty(sr.getContent())) {
			sr.setMessage(ErrorCodes.CONTENT_ILLEGAL.desc());
			sr.setState(SmsState.ERROR.key());
			persist(sr);
			return Result.error(ErrorCodes.CONTENT_ILLEGAL).setObject(sr);
		}

		if (checkFrequency) {
			if (!Strings.isNullOrEmpty(sr.getIp()) || !"127.0.0.1".equals(sr.getIp())) {
				// 能够获取IP,做IP限制
				if (ipSendSmsCount(sr.getIp(), Sms.sameIpInterval()) > 0) {
					return Result.error(ErrorCodes.IP_BUSY).setObject(sr);
				}
			}

			// 对号码的发送时间做限制
			if (phoneSmsCount(sr.getPhone(), Sms.samePhoneInterval()) > 0) {
				return Result.error(ErrorCodes.PHONE_BUSY).setObject(sr);
			}
		}

		Map<String, Object> externalParams = Maps.newHashMap();
		SmsProvider provider = getProvider(sr.getProvider());
		for (SmsProviderAuthKey key : provider.authKeys()) {
			String value = configService.query(key).stringValue();
			externalParams.put(key.authKey(), value);
		}
		// 开始发送
		SmsSendResult sendResult = null;
		if (sr.stateEqual(SmsState.PLANNING)) {
			if (sr.getPlan_time() == null || sr.getPlan_time().getTime() <= System.currentTimeMillis()) {
				// 立即发送
				sendResult = provider.send(sr.getPhone(), sr.getContent(), externalParams);
			} else {
				// 计划发送
				persist(sr);
				// 添加到monitor池
				smsMonitor.add(sr);
				return Result.success(sr);
			}
		} else if (sr.getState() == null) {
			// 立即发送
			sendResult = provider.send(sr.getPhone(), sr.getContent(), externalParams);
		} else {
			// 成功或者完成，忽略

		}

		if (sendResult != null) {
			// 执行了发送任务
			// 合并发送结果
			sr.setOuter_code(sendResult.getOuter_code());
			sr.setOuter_id(sendResult.getOuter_id());
			sr.setMessage(sendResult.getMessage());
			sr.setRemark(sendResult.getRemark());
			if (sendResult.isSuccess()) {
				sr.setState(SmsState.SUCCESS.key());
			} else {
				sr.setState(SmsState.ERROR.key());
			}
			persist(sr);

		}

		if (sendResult.isSuccess()) {
			return Result.success(sr);
		} else {
			return Result.error(sr.getOuter_code(), sr.getMessage());
		}
	}
	
	public Result<SmsRecord> send(String phone, Date planTime, String content, String business_type, boolean checkFrequency){
		String ip = WebUtils.getCurrentIp();
		// 组装短信记录
		SmsRecord sr = new SmsRecord();
		sr.setPhone(phone);
		sr.setPlan_time(planTime);
		sr.setProvider(defaultProvider().getName());
		sr.setContent(content);
		sr.setIp(ip);
		sr.setBusiness_type(business_type);

		return sendSms(sr, checkFrequency);
	}
	
	public Result<SmsRecord> send(String phone, Date planTime, String templateId, boolean checkFrequency, String...args){
		// 解析模板
		String content = Sms.template(templateId, args);
		return send(phone, planTime, content,templateId, checkFrequency);
	}

	public Result<SmsRecord> send(String phone, String templateId, boolean checkFrequency, String... args){
		return send(phone, null, templateId, checkFrequency, args);
	}
	
	public Result<SmsRecord> send(String phone, String templateId, String... args) {
		return send(phone, null, templateId, true, args);
	}

	public Result<SmsRecord> send(String phone, Date plannedTime, String templateId, String... args) {
		return send(phone, plannedTime, templateId, true, args);
	}

	private SmsRecord persist(SmsRecord sr) {
		if (sr != null) {
			if (sr.getId() != null) {
				mysqlService.update(sr);
			} else {
				sr.setCreate_time(new Date());
				mysqlService.insert(sr);
			}
		}
		return sr;
	}

	public int ipSendSmsCount(String ip, long offsetNow) {
		if (Strings.isNullOrEmpty(ip) || "127.0.0.1".equalsIgnoreCase(ip) || "0:0:0:0".equalsIgnoreCase(ip)) {
			return 0;
		}
		Date offset = Dates.addMillisecond(new Date(), (int) -offsetNow);
		List<SmsRecord> reses = mysqlService.queryBy(SmsRecord.class,
				"ip='" + Strings.nullToEmpty(ip) + "' AND `state`='" + SmsState.SUCCESS.key()
						+ "' AND send_time IS NOT NULL AND send_time > '" + FORMAT_DATETIME.format(offset) + "'");
		return reses.size();
	}

	public int phoneSmsCount(String phone, long offsetNow) {
		Date offset = Dates.addMillisecond(new Date(), (int) -offsetNow);
		List<SmsRecord> reses = mysqlService.queryBy(SmsRecord.class,
				"phone ='" + Strings.nullToEmpty(phone) + "' AND `state`='" + SmsState.SUCCESS.key()
						+ "' AND send_time IS NOT NULL AND send_time > '" + FORMAT_DATETIME.format(offset) + "'");
		return reses.size();
	}

	public List<? extends SmsRecord> search(Class<? extends SmsRecord> cls, SmsProvider smsProvider, SmsState state,
			String phone, Date fromDate, Date toDate, Integer maxRecords) {
		maxRecords = maxRecords == null ? BaseEntity.MAX_QUERY_RECOREDS : maxRecords;

		StringBuffer where = new StringBuffer(" 1=1 ");
		if (smsProvider != null) {
			where.append(" AND provider='" + smsProvider.getName() + "'");
		}

		if (state != null) {
			where.append(" AND `state` = '" + state.key() + "'");
		}
		if (!Strings.isNullOrEmpty(phone) && !Strings.nullToEmpty(phone).contains("'")) {
			where.append(" AND phone LIKE '%" + phone + "%'");
		}

		if (fromDate != null) {
			where.append(" AND create_time >= timestamp('" + FORMAT_DATE.format(fromDate) + " 00:00:00')");
		}

		if (toDate != null) {
			where.append(" AND create_time <= timestamp('" + FORMAT_DATE.format(toDate) + " 23:59:59')");
		}
		// 排序
		where.append(" ORDER BY create_time DESC ");

		// 组装分页
		where.append(" LIMIT " + maxRecords);
		List<? extends SmsRecord> result = mysqlService.queryBy(cls, where.toString());

		return result;
	}
}
