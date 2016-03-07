package cn.cerestech.framework.platform.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum ErrorCodes implements DescribableEnum {
	PLATFORM_KEY_AND_SECRET_IS_REQUIRED("PLATFORM_KEY_AND_SECRET_IS_REQUIRED", "需要指定platform_key和secret"), //
	PLATFORM_AUTH_INCORRECT("PLATFORM_AUTH_INCORRECT", "key & secret 校验错误"), //
	PLATFORM_AUTH_EXPIRED("PLATFORM_AUTH_EXPIRED", "平台授权已过期"), //
	PLATFORM_TOKEN_EXPIRED("PLATFORM_TOKEN_EXPIRED", "Token已过期"),//
	;

	private String key, desc;

	ErrorCodes(String key, String desc) {
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
