package cn.cerestech.middleware.sms.providers;

import java.util.Map;

import cn.cerestech.framework.core.Provider;
import cn.cerestech.middleware.sms.entity.SmsSendResult;
import cn.cerestech.middleware.sms.enums.SmsProviderAuthKey;

public interface SmsProvider extends Provider{

	public String getName();

	public SmsProviderAuthKey[] authKeys();

	public SmsSendResult send(String phone, String text, Map<String, Object> configParams);

}
