package cn.cerestech.middleware.location.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum AdminLevel implements DescribableEnum {
	PROVINCE("PROVINCE", "省"), //
	CITY("CITY", "市"), //
	COUNTY("COUNTY", "县"),//
	;
	private String key, desc;

	private AdminLevel(String key, String desc) {
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
