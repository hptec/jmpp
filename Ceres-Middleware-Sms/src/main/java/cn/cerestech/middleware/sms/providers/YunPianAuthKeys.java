package cn.cerestech.middleware.sms.providers;

import cn.cerestech.middleware.sms.enums.SmsProviderAuthKey;

public enum YunPianAuthKeys implements SmsProviderAuthKey {
	APIKEY("SMS_PROVIDER_YUNPIAN_APIKEY", "apikey", "", "云片网络授权密钥");

	YunPianAuthKeys(String key, String authKey, String defaultValue, String desc) {
		this.key = key;
		this.authKey = authKey;
		this.defaultValue = defaultValue;
		this.desc = desc;
	}

	private String key, authKey, defaultValue, desc;

	@Override
	public String key() {
		return key;
	}

	@Override
	public String defaultValue() {
		return defaultValue;
	}

	@Override
	public String desc() {
		return desc;
	}

	@Override
	public String authKey() {
		return authKey;
	}

}
