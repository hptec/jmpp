package cn.cerestech.middleware.sms.providers;

import java.util.Map;

import cn.cerestech.framework.core.parser.Parser;
import cn.cerestech.middleware.sms.entity.SmsSendResult;
import cn.cerestech.middleware.sms.enums.SmsProviderAuthKey;

public interface SmsProvider {

	public String getName();

	public SmsProviderAuthKey[] authKeys();

	public SmsSendResult send(String phone, String text, Map<String, Object> configParams);

	public Parser getParser();

}
