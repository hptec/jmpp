package cn.cerestech.middleware.balance.enums;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.enums.ConfigKey;

public enum BalanceConfigKeys implements ConfigKey {
	BALANCE_WITHDRAW_BANKCARD_ENABLED("BALANCE_WITHDRAW_BANKCARD_ENABLED", "Y", "是否开启银行卡提现方式"), //
	BALANCE_WITHDRAW_MP_ENABLED("BALANCE_WITHDRAW_MP_ENABLED", "N", "是否开启微信提现方式"), //

	BALANCE_WITHDRAW_ACCOUNT("BALANCE_WITHDRAW_ACCOUNT", "", "提现账户类型"), // 一个系统只能唯一

	BALANCE_WITHDRAW_SINGLE_LIMIT("BALANCE_WITHDRAW_SINGLE_LIMIT", "5000", "单笔限额"), //
	BALANCE_WITHDRAW_REPEAT_SUBMIT("BALANCE_WITHDRAW_REPEAT_SUBMIT", "N", "是否可以重复提交"), //
	BALANCE_WITHDRAW_FEE_PER_SUBMIT("BALANCE_WITHDRAW_FEE_PER_SUBMIT", "2", "单笔手续费"), //
	BALANCE_WITHDRAW_FEE_RATIO("BALANCE_WITHDRAW_FEE_RATIO", "0", "提现费率"),//
	BALANCE_WITHDRAW_SMS_OPEN("BALANCE_WITHDRAW_SMS_OPEN", "Y", "提现短信通知是否打开，包括提现申请和提现成功,Y打开通知，N关闭通知"),//
	;
	private String key, defaultValue, desc;

	private BalanceConfigKeys(String key, String defaultValue, String desc) {
		this.key = key;
		this.defaultValue = defaultValue;
		this.desc = desc;
	}

	@Override
	public String key() {
		return key;
	}

	@Override
	public String defaultValue() {
		return defaultValue;
	}

	@Override
	public String desc() {
		return desc;
	}

	public static BalanceConfigKeys keyOf(String key) {
		for (BalanceConfigKeys s : BalanceConfigKeys.values()) {
			if (s.key.equalsIgnoreCase(key)) {
				return s;
			}
		}
		return null;
	}

	public static List<KV> toList() {
		List<KV> kv = Lists.newArrayList();
		for (BalanceConfigKeys s : BalanceConfigKeys.values()) {
			kv.add(KV.on().put("key", s.key()).put("desc", s.desc()).put("value", s.defaultValue()));
		}
		return kv;
	}

}