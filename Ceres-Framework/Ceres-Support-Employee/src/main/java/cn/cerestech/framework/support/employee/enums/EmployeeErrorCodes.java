package cn.cerestech.framework.support.employee.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum EmployeeErrorCodes implements DescribableEnum {
	EMPLOYEE_NOT_EXIST("EMPLOYEE_NOT_EXIST", "员工不存在"), //
	PASSWORD_CANNOT_EMPTY("PASSWORD_CANNOT_EMPTY", "密码不能为空"), //
	PASSWORD_NOT_EQUAL("PASSWORD_NOT_EQUAL", "两次输入密码不同，请确认输入正确！"), //
	NAME_REQUIRED("NAME_REQUIRED", "名称为必填项目"),//
	LOGINID_CONFLICT("LOGINID_CONFLICT","当前登录名已经被其他账号使用"),//
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