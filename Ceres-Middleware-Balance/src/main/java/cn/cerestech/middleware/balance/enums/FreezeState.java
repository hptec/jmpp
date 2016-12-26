package cn.cerestech.middleware.balance.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum FreezeState implements DescribableEnum {
	FREEZING("FREEZING", "冻结中"), 
	UNFREEZE("UNFREEZE", "已解冻");
	private String key, desc;

	private FreezeState(String key, String desc) {
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