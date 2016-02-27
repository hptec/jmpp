package cn.cerestech.framework.support.application.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum ApplicationKey implements DescribableEnum {
	SUPPORT_APPLICATION_APPID("appid", "ID"), //
	SUPPORT_APPLICATION_APPSECRET("appsecret", "密钥"),//
	SUPPORT_APPLICATION_ACCESS_TOKEN("access_token", "访问令牌"), //
	

	;
	private String key, desc;

	private ApplicationKey(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String key() {
		return key;
	}

	@Override
	public String desc() {
		return desc;
	}

}