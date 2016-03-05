package cn.cerestech.middleware.carouseladvertising.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum ErrorCodes implements DescribableEnum {
	NAMESPACE_NOT_EXIST("NAMESPACE_NOT_EXIST", "分类不存在"), //
	URI_NOT_EXIST("URI_NOT_EXIST", "图片存储路径不存在"), //
	PIC_NOT_EXIST("PIC_NOT_EXIST", "图片不存在"),//
	;
	private String key, desc;

	private ErrorCodes(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String key() {
		return key;
	}

	public String desc() {
		return desc;
	}

}