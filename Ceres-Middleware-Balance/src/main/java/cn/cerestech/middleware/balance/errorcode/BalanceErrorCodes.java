package cn.cerestech.middleware.balance.errorcode;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum BalanceErrorCodes implements DescribableEnum {

	ACCOUNT_NOT_FOUND("ACCOUNT_NOT_FOUND", "未能查询到账户信息"), //
	ACCOUNT_OUT_BANNED("ACCOUNT_OUT_BANNED", "账户禁止转出"), //
	AMOUNT_NOT_ENOUGH("AMOUNT_NOT_ENOUGH", "账户余额不足"), //

	ACCOUNT_RECORD_NOT_FOUND("ACCOUNT_RECORD_NOT_FOUND", "未能查询到获得记录"),//

	;

	private String key, desc;

	private BalanceErrorCodes(String key, String desc) {
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