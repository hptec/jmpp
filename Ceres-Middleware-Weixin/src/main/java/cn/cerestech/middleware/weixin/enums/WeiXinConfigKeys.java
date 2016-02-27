package cn.cerestech.middleware.weixin.enums;

import cn.cerestech.framework.support.configuration.enums.ConfigKey;

public enum WeiXinConfigKeys implements ConfigKey {
	WEIXIN_APP_ID("WEIXIN_APP_ID", "", "微信公众号APPID"), //
	WEIXIN_APP_SECRET("WEIXIN_APP_SECRET", "", "微信公众号APP密钥"),//
	;

	private String key, defaultValue, desc;

	private WeiXinConfigKeys(String key, String defaultValue, String desc) {
		this.key = key;
		this.desc = desc;
		this.defaultValue = defaultValue;
	}

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

}