package cn.cerestech.framework.core.exporter;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum ExportColumFormat implements DescribableEnum {

	BOOLEAN("BOOLEAN", "是否"), //
	DATE("DATE", "日期"), //
	BIGDECIMAL("BIGDECIMAL", "数字"), //
	LONG("LONG", "长整数"), //
	INTEGER("INTEGER", "整形"), //
	TEXT("TEXT", "文本"), //
	RICHTEXT("RICHTEXT", "富文本"), //
	ENUM("ENUM", "枚举"), //
	PERCENTAGE("PERCENTAGE", "百分数"),//
	;
	private String key, desc;

	private ExportColumFormat(String key, String desc) {
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
