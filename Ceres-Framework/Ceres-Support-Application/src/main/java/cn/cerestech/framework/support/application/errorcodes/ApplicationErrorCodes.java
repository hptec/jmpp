package cn.cerestech.framework.support.application.errorcodes;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum ApplicationErrorCodes implements DescribableEnum {
	APPID_OR_APPSECRET_ERROR("APPID_OR_APPSECRET_ERROR", "app_id或app_secret错误"), //
	ACCESS_TOKEN_EXPIRED("ACCESS_TOKEN_EXPIRED", "access_token过期"),//
	;
	private String key, desc;

	private ApplicationErrorCodes(String key, String desc) {
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