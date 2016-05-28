package cn.cerestech.middleware.sms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beust.jcommander.internal.Maps;
import com.google.common.base.Strings;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.configuration.service.ConfigService;
import cn.cerestech.middleware.sms.dao.SmsBatchDao;
import cn.cerestech.middleware.sms.dao.SmsDao;
import cn.cerestech.middleware.sms.entity.SmsBatch;
import cn.cerestech.middleware.sms.entity.SmsRecord;
import cn.cerestech.middleware.sms.entity.SmsSendResult;
import cn.cerestech.middleware.sms.enums.ErrorCodes;
import cn.cerestech.middleware.sms.enums.SmsConfigKeys;
import cn.cerestech.middleware.sms.enums.SmsProvider;
import cn.cerestech.middleware.sms.enums.SmsProviderAuthKey;
import cn.cerestech.middleware.sms.enums.SmsState;
import cn.cerestech.middleware.sms.providers.ISmsProvider;
import cn.cerestech.middleware.sms.providers.ISmsSender;

@Service
public class SmsMessageService {

	@Autowired
	ConfigService configService;

	@Autowired
	SmsDao smsDao;

	@Autowired
	SmsBatchDao smsBatchDao;

	/**
	 * 是否开启短信发送功能
	 * 
	 * @return
	 */
	public Boolean isSMSEnabled() {
		return configService.query(SmsConfigKeys.SMS_ENABLE).boolValue();
	}

	public Result<SmsBatch> send(SmsBatch batch) {
		smsBatchDao.save(batch);
		List<SmsRecord> records = batch.getRecords();
		for (SmsRecord sms : records) {

			// 采用统一设置的时间
			if (sms.getPlanTime() == null && batch.getPlanTime() != null) {
				sms.setPlanTime(batch.getPlanTime());
			}
			// 设置Provider
			SmsProvider provider = sms.getProvider() != null ? sms.getProvider() : batch.getProvider();
			sms.setProvider(provider);
			// 解析内容
			if (Strings.isNullOrEmpty(sms.getContent())) {
				String template = !Strings.isNullOrEmpty(sms.getContentTemplate()) ? sms.getContentTemplate()
						: batch.getTemplate();
				sms.setContent(provider.parser().parse(template, sms.getContentParameter()));
			}

		}
		return null;
	}

	public Result<SmsRecord> send(SmsRecord sms) {
		if (!isSMSEnabled()) {
			return Result.success();
		}
		if (sms == null) {
			SmsSendResult result = new SmsSendResult();
			result.setSuccess(Boolean.FALSE);
			result.setMessage(ErrorCodes.CONTENT_ILLEGAL.desc());
			return Result.error(ErrorCodes.CONTENT_ILLEGAL);
		}

		if (sms.getProvider() == null) {
			SmsSendResult result = new SmsSendResult();
			result.setSuccess(Boolean.FALSE);
			result.setMessage(ErrorCodes.SMS_PROVIDER_NOT_EXSIT.desc());
			sms.setResult(result);
			smsDao.save(sms);
			return Result.error(ErrorCodes.SMS_PROVIDER_NOT_EXSIT);
		}

		// 检查内容
		if (Strings.isNullOrEmpty(sms.getContent())) {
			if (!Strings.isNullOrEmpty(sms.getContentTemplate())) {
				String content = sms.getProvider().parser().parse(sms.getContentTemplate(), sms.getContentParameter());
				sms.setContent(content);
			}
		}
		// 没有内容返回错误
		if (Strings.isNullOrEmpty(sms.getContent())) {
			SmsSendResult result = new SmsSendResult();
			result.setSuccess(Boolean.FALSE);
			result.setMessage(ErrorCodes.CONTENT_ILLEGAL.desc());
			sms.setResult(result);
			smsDao.save(sms);
			return Result.error(ErrorCodes.CONTENT_ILLEGAL);
		}

		// 是否及时发送
		if (sms.getPlanTime() != null && sms.getPlanTime().after(new Date())) {
			// 延时发送
			smsDao.save(sms);
		} else {
			// 实时发送
			Map<String, Object> param = Maps.newHashMap();
			for (SmsProviderAuthKey key : sms.getProvider().authKeys()) {
				param.put(key.authKey(), configService.query(key).stringValue());
			}
			sms.setSendedTime(new Date());
			ISmsSender sender = sms.getProvider().sender();
			SmsSendResult result = sender.send(sms, param);
			sms.setResult(result);
			smsDao.save(sms);
		}

		return Result.success(sms);
	}

	public int ipSendSmsCount(String ip, long offsetNow) {
		return 1;
	}

	public int phoneSmsCount(String phone, long offsetNow) {
		return 1;
	}

	public List<? extends SmsRecord> search(Class<? extends SmsRecord> cls, ISmsProvider smsProvider, SmsState state,
			String phone, Date fromDate, Date toDate, Integer maxRecords) {

		StringBuffer where = new StringBuffer(" 1=1 ");
		if (smsProvider != null) {
			// where.append(" AND provider='" + smsProvider.getName() + "'");
		}

		if (state != null) {
			where.append(" AND `state` = '" + state.key() + "'");
		}
		if (!Strings.isNullOrEmpty(phone) && !Strings.nullToEmpty(phone).contains("'")) {
			where.append(" AND phone LIKE '%" + phone + "%'");
		}

		if (fromDate != null) {
		}

		if (toDate != null) {
		}
		// 排序
		where.append(" ORDER BY create_time DESC ");

		// 组装分页
		where.append(" LIMIT " + maxRecords);

		return null;
	}
}
