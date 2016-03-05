package cn.cerestech.middleware.article.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum ErrorCodes implements DescribableEnum {
	ARTICLE_NOT_EXIST("ARTICLE_NOT_EXIST", "文章不存在"), //
	TITLE_REQUIRED("TITLE_REQUIRED", "请填写标题"), //
	TITLE_LENGTH_LESS_THAN_200("TITLE_LENGTH_LESS_THAN_200", "标题必须少于200字"), //
	CATEGORY_REQUIRED("CATEGORY_REQUIRED", "请选择文章分类"),//
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