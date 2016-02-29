package cn.cerestech.middleware.balance.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum TransactionStatus implements DescribableEnum {
	PROCESSING("PROCESSING", "交易中"), //
	FINISH("FINISH", "完成"), //
	ROLLBACK("ROLLBACK", "已取消")//
	;
	private String key, desc;

	private TransactionStatus(String key, String desc) {
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