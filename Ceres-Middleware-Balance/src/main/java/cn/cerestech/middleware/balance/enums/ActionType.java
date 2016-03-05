package cn.cerestech.middleware.balance.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum ActionType implements DescribableEnum {
	IN("IN", "入账"), //
	OUT("OUT", "出账"), //
	;
	private String key, desc;

	private ActionType(String key, String desc) {
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