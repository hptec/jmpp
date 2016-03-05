package cn.cerestech.framework.platform.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum PlatformCategory implements DescribableEnum {
	CONSOLE("console", "管理台"), //
	APP("app", "手机移动端"), //
	MP("mp", "微信公众号"), //
	FRONTEND("frontend", "网站")

	;
	private String key, desc;

	private PlatformCategory(String key, String desc) {
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