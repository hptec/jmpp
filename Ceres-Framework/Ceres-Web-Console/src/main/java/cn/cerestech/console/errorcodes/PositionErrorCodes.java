package cn.cerestech.console.errorcodes;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum PositionErrorCodes implements DescribableEnum {
	POSITION_USED_BY_EMPLOYEE("POSITION_USED_BY_EMPLOYEE", "有员工属于此岗位"), //

	;
	private String key, desc;

	private PositionErrorCodes(String key, String desc) {
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