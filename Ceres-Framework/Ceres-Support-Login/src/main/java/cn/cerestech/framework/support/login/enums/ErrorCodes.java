package cn.cerestech.framework.support.login.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum ErrorCodes implements DescribableEnum {
	LOGIN_FAILED("LOGIN_FAILED", "用户名或密码错误"), //
	LOGIN_FROZEN("LOGIN_FROZEN", "用户已经被冻结，请联系管理员"), //
	LOGIN_REQUIRED("LOGIN_REQUIRED", "需要登录"), //
	SMS_CODE_ERROR("SMS_CODE_ERROR", "短信验证码错误"), // 短信校验登录时使用
	PROVIDER_NOT_SUPPORT("PROVIDER_NOT_SUPPORT", "不支持此种登录方式"),//
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
