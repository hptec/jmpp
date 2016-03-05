package cn.cerestech.middleware.sms.providers;

import cn.cerestech.middleware.sms.enums.SmsProviderAuthKey;

public enum HuoniAuthKeys implements SmsProviderAuthKey {
	ACCOUNT("SMS_PROVIDER_HUONI_ACCOUNT", "account", "", "发送账户名称"), //
	PASSWORD("SMS_PROVIDER_HUONI_PASSWORD", "password", "", "发送账户密码"),//

	;

	HuoniAuthKeys(String key, String authKey, String defaultValue, String desc) {
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
