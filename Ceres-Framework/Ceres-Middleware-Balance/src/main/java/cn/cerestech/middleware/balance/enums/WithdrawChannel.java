package cn.cerestech.middleware.balance.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum WithdrawChannel implements DescribableEnum {
	BANK("BANK", "银行卡"),//
	// MP("MP", "微信公众号"),//
	;
	private String key, desc;

	private WithdrawChannel(String key, String desc) {
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