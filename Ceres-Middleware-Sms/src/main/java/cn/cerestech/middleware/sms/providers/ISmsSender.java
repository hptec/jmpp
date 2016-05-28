package cn.cerestech.middleware.sms.providers;

import java.util.Map;

import cn.cerestech.middleware.sms.entity.SmsRecord;
import cn.cerestech.middleware.sms.entity.SmsSendResult;

public interface ISmsSender {

	public SmsSendResult send(SmsRecord record, Map<String, Object> param);
}
