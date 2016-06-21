package cn.cerestech.framework.support.storage.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum ErrorCodes implements DescribableEnum {

	FILE_NOT_FOUND("FILE_NOT_FOUND", "文件未找到"), //

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