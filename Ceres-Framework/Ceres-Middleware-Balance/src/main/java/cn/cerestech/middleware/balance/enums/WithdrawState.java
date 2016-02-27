package cn.cerestech.middleware.balance.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum WithdrawState implements DescribableEnum {
	SUBMIT("SUBMIT", "提交"), DENY("DENY", "拒绝"), APPROVED("APPROVED", "通过"), FINISH("FINISH", "完成"), CANCEL("CANCEL",
			"取消");
	private String key, desc;

	private WithdrawState(String key, String desc) {
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