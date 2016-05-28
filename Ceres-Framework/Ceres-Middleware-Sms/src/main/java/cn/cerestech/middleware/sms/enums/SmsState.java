package cn.cerestech.middleware.sms.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum SmsState implements DescribableEnum {
	PLANNING("PLAN", "计划"), SUCCESS("SUCCESS", "成功"), ERROR("ERROR", "错误");
	private String key, desc;

	private SmsState(String key, String desc) {
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