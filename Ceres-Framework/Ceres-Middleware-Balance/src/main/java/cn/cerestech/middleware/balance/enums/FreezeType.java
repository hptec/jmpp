package cn.cerestech.middleware.balance.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum FreezeType implements DescribableEnum {
	FREEZE("FREEZE", "冻结"), UNFREEZE("Unfrozen", "已解冻"), ROLLBACK("Rollback", "解冻退回");
	private String key, desc;

	private FreezeType(String key, String desc) {
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