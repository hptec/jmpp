package cn.cerestech.framework.support.login.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum ErrorCodes implements DescribableEnum {
	PLATFORM_CATEGORY_REQUIRED("PLATFORM_CATEGORY_REQUIRED", "需要指定平台类别"), //
	LOGIN_REQUIRED("LOGIN_REQUIRED", "需要登录"),//
	;

	ErrorCodes(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	private String key, desc;

	@Override
	public String key() {
		return key;
	}

	@Override
	public String desc() {
		return desc;
	}

}
