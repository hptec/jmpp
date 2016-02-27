package cn.cerestech.middleware.helpcenter.simple.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum ErrorCodes implements DescribableEnum {
	CATEGORY_NAME_NOT_EXIST("CATEGORY_NAME_NOT_EXIST", "请填写分类名称"), //
	CATEGORY_PARENT_REQUIRED("CATEGORY_PARENT_REQUIRED", "请选择上级分类"),//
	CATEGORY_HAS_CHILDREN_CANNOT_REMOVE("CATEGORY_HAS_CHILDREN_CANNOT_REMOVE","分类下尚有子分类，不能删除"),//
	CATEGORY_HAS_QUESTION_CANNOT_REMOVE("CATEGORY_HAS_QUESTION_CANNOT_REMOVE","分类下尚有问题，不能删除"),//
	
	QUESTION_TITLE_REQUIRED("QUESTION_TITLE_REQUIRED", "请填写问题标题"), //
	QUESTION_CATEGORY_REQUIRED("QUESTION_CATEGORY_REQUIRED", "请选择问题分类"), //
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