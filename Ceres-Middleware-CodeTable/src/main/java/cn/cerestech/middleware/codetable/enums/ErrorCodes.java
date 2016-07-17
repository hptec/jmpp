package cn.cerestech.middleware.codetable.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum ErrorCodes implements DescribableEnum {

	NOT_FOUND("NOT_FOUND", "找不到对应的字典"), //

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
}