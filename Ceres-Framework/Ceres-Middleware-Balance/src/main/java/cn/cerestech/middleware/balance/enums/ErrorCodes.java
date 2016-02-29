package cn.cerestech.middleware.balance.enums;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.enums.DescribableEnum;

public enum ErrorCodes implements DescribableEnum {
	AMOUNT_NOT_ENOUGH("AMOUNT_NOT_ENOUGH", "账户余额不足"), //
	AMOUNT_ADD_POSITIVE_ZERO_ONLY("AMOUNT_ADD_POSITIVE_ZERO_ONLY", "添加账户余额只能为零或正数"), //
	AMOUNT_SUBSTRACT_NEGETIVE_ZERO_ONLY("AMOUNT_SUBSTRACT_NEGETIVE_ZERO_ONLY", "减少账户余额只能为零或负数"), //
	FREEZE_NOT_FOUND("FREEZE_NOT_FOUND", "冻结记录未找到"), //
	WITHDRAW_WAY_NOT_SUPPORTED("WITHDRAW_WAY_NOT_SUPPORTED", "提现方式不支持"), //

	BANKCARD_UNAVAILABLE("BANKCARD_UNAVAILABLE", "银行卡不可用或不存在"), //
	BANKCARD_OWNER_CANNOT_EMPTY("BANKCARD_OWNER_CANNOT_EMPTY", "银行卡所有者不能为空"), //
	BANKCARD_NAME_CANNOT_EMPTY("BANKCARD_NAME_CANNOT_EMPTY", "银行卡户名不能为空"), //
	BANKCARD_CODE_NOTEQUAL("BANKCARD_CODE_NOTEQUAL", "卡号不存在或不相等"), //
	BANKCARD_CODE_1619_REQUIRED("BANKCARD_CODE_1619_REQUIRED", "请输入16、19位银行卡号码"), //
	BANKCARD_BANKNAME_REQUIRED("BANKCARD_BANKNAME_REQUIRED", "需要指定银行名称"), //
	BANKCARD_BANKBRANCH_REQUIRED("BANKCARD_BANKBRANCH_REQUIRED", "需要填写支行"), //
	BANKCARD_PROVINCE_CITY_REQUIRED("BANKCARD_PROVINCE_CITY_REQUIRED", "需要选择开户城市"), //

	WITHDRAW_NOT_FOUND("WITHDRAW_NOT_FOUND", "提现记录未找到"), //
	WITHDRAW_BALANCE_TYPE_REQUIRED("WITHDRAW_BALANCE_TYPE_REQUIRED", "需要设置提现账户类型"), //
	WITHDRAW_NOT_ALLOWED("WITHDRAW_NOT_ALLOWED", "不允许提现"), //
	WITHDRAW_AMOUNT_OVER_LIMIT("WITHDRAW_AMOUNT_OVER_LIMIT", "提现金额超过限额"), //
	WITHDRAW_ONLY_ONE_LIMIT("WITHDRAW_ONLY_ONE_LIMIT", "已有提现记录存在，不允许重复提交"), //
	WITHDRAW_NO_FACT_AMOUNT_LEFT("WITHDRAW_NO_FACT_AMOUNT_LEFT", "实得金额小于等于0，请重新提交"), //

	TRANS_IS_EMPTY("TRANS_IS_EMPTY", "交易事务为空"),//
	;
	private String key, desc;

	private ErrorCodes(String key, String desc) {
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

	public static ErrorCodes keyOf(String key) {
		for (ErrorCodes s : ErrorCodes.values()) {
			if (s.key.equalsIgnoreCase(key)) {
				return s;
			}
		}
		return null;
	}

	public static List<KV> toList() {
		List<KV> kv = Lists.newArrayList();
		for (ErrorCodes s : ErrorCodes.values()) {
			kv.add(KV.on().put("key", s.key()).put("desc", s.desc()));
		}
		return kv;
	}
}