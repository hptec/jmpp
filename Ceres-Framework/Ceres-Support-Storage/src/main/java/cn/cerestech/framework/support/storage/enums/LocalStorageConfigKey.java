package cn.cerestech.framework.support.storage.enums;

import cn.cerestech.framework.support.configuration.enums.ConfigKey;

public enum LocalStorageConfigKey implements ConfigKey {

	FRAMEWORK_SUPPORT_LOCALSTORAGE_PATH("FRAMEWORK_SUPPORT_LOCALSTORAGE_PATH", "/filestorage", "资源文件存储路径"), //
	//
	;

	private String key, defaultValue, desc;

	private LocalStorageConfigKey(String key, String defaultValue, String desc) {
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