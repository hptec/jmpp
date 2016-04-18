package cn.cerestech.support.web.console.errorcode;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum EmployeeErrorCodes implements DescribableEnum {
	LOGIN_FAILED("LOGIN_FAILED", "用户不存在或密码错误"), //
	EMPLOYEE_FROZEN("EMPLOYEE_FROZEN", "用户已经被冻结，请联系管理员"), //
	PASSWORD_CANNOT_EMPTY("PASSWORD_CANNOT_EMPTY", "密码不能为空"), //
	PASSWORD_NOT_EQUAL("PASSWORD_NOT_EQUAL", "两次输入密码不同，请确认输入正确！"),//
	;

	private String key, desc;

	private EmployeeErrorCodes(String key, String desc) {
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