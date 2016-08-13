package cn.cerestech.framework.support.starter.console.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum EmployeeErrorCodes implements DescribableEnum {
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