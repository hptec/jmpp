package cn.cerestech.console.errorcodes;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum SysRoleErrorCodes implements DescribableEnum {
	ROLE_IS_EMPTY("ROLE_IS_EMPTY", "角色为空"), //
	ROLE_NOT_EXIST("ROLE_NOT_EXIST", "角色不存在"), //
	HAS_EMPLOYEE_CANNOT_DELETE("HAS_EMPLOYEE_CANNOT_DELETE", "尚有员工分配此角色，不可删除"), //
	HAS_MENU_CANNOT_DELETE("HAS_MENU_CANNOT_DELETE", "角色下尚有菜单，不可删除"),//
	;
	private String key, desc;

	private SysRoleErrorCodes(String key, String desc) {
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