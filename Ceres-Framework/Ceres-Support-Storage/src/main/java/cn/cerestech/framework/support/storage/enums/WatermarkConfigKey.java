package cn.cerestech.framework.support.storage.enums;

import cn.cerestech.framework.support.configuration.enums.ConfigKey;

public enum WatermarkConfigKey implements ConfigKey {

	FRAMEWORK_SUPPORT_LOCALSTORAGE_WATERMARK_FILE_PATH("FRAMEWORK_SUPPORT_LOCALSTORAGE_WATERMARK_FILE_PATH", "",
			"图片水印图片绝对路径"),
	//
	;

	private String key, defaultValue, desc;

	private WatermarkConfigKey(String key, String defaultValue, String desc) {
		this.key = key;
		this.desc = desc;
		this.defaultValue = defaultValue;
	}

	@Override
	public String key() {
		return key;
	}

	@Override
	public String defaultValue() {
		return defaultValue;
	}

	@Override
	public String desc() {
		return desc;
	}

}