package cn.cerestech.console.errorcodes;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum DepartmentErrorCodes implements DescribableEnum {
	DEPARTMENT_NOT_EXIST("DEPARTMENT_NOT_EXIST", "部门不存在"), //
	HAS_CHILDREN_CANNOT_REMOVE("HAS_CHILDREN_CANNOT_REMOVE", "拥有下级部门不可删除"),//
	;
	private String key, desc;

	private DepartmentErrorCodes(String key, String desc) {
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