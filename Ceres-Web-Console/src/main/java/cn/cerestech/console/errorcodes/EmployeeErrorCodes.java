package cn.cerestech.console.errorcodes;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum EmployeeErrorCodes implements DescribableEnum {
	LOGIN_FAILED("LOGIN_FAILED", "用户不存在或密码错误"), //
	EMPLOYEE_FROZEN("EMPLOYEE_FROZEN", "用户已经被冻结，请联系管理员"), //
	PASSWORD_CANNOT_EMPTY("PASSWORD_CANNOT_EMPTY", "密码不能为空"), //
	PASSWORD_NOT_EQUAL("PASSWORD_NOT_EQUAL","两次输入密码不同，请确认输入正确！"),//
	WITHDRAW_WAY_NOT_SUPPORTED("WITHDRAW_WAY_NOT_SUPPORTED", "提现方式不支持"), BANKCARD_UNAVAILABLE("BANKCARD_UNAVAILABLE",
			"银行卡不可用或不存在"), WITHDRAW_NOT_FOUND("WITHDRAW_NOT_FOUND", "提现记录未找到");
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