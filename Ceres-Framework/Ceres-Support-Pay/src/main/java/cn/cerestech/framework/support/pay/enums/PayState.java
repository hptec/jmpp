package cn.cerestech.framework.support.pay.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;


public enum PayState implements DescribableEnum {
	NEW("NEW", "新创建"), 
	SUCCESS("SUCCESS", "完成"), 
	FAIL("FAIL", "失败");
	private String key, desc;

	private PayState(String key, String desc) {
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
