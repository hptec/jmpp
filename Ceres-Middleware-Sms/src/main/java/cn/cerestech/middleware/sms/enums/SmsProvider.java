package cn.cerestech.middleware.sms.enums;

import java.util.Map;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.core.parser.Parser;
import cn.cerestech.framework.core.parser.PropertiesTemplateParser;
import cn.cerestech.middleware.sms.entity.SmsSendResult;
import cn.cerestech.middleware.sms.providers.ISmsProvider;

public enum SmsProvider implements DescribableEnum, ISmsProvider {
	YUNPIAN("云片网络", "云片网络", PropertiesTemplateParser.fromProperties("sms")), //
	;

	private String key, desc;
	private Parser parser;

	private SmsProvider(String key, String desc, Parser parser) {
		this.key = key;
		this.desc = desc;
		this.parser = parser;
	}

	@Override
	public String key() {
		return key;
	}

	@Override
	public String desc() {
		return desc;
	}

	@Override
	public SmsProviderAuthKey[] authKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmsSendResult send(String phone, String text, Map<String, Object> configParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Parser getParser() {
		return parser;
	}

}
