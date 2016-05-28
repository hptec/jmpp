package cn.cerestech.middleware.location.mobile;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.middleware.location.nation.Nation;

public enum CallingCode implements DescribableEnum {
	CHINA("86", Nation.CHINA)//
	;

	private String key, desc;

	private Nation nation;

	private CallingCode(String key, Nation nation) {
		this.key = key;
		this.desc = nation.desc();
		this.nation = nation;
	}

	@Override
	public String desc() {
		return desc;
	}

	@Override
	public String key() {
		return key;
	}

	public Nation nation() {
		return nation;
	}

}
