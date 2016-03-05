package cn.cerestech.console.enums;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.support.configuration.enums.ConfigKey;

public enum ConsoleConfigKey implements ConfigKey {
	EMPLOYEE_ACTION_LOG_ENABLE("EMPLOYEE_ACTION_LOG_ENABLE", "N", "是否开启后台行为日志"), //
	//
	;

	private String key, defaultValue, desc;

	private ConsoleConfigKey(String key, String defaultValue, String desc) {
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

	public static ConsoleConfigKey keyOf(String key) {
		for (ConsoleConfigKey s : ConsoleConfigKey.values()) {
			if (s.key.equals(key)) {
				return s;
			}
		}
		return null;
	}

	public static List<KV> toList() {
		List<KV> kv = Lists.newArrayList();
		for (ConsoleConfigKey s : ConsoleConfigKey.values()) {
			kv.add(KV.on().put("key", s.key()).put("desc", s.desc()).put("value", s.defaultValue()));
		}
		return kv;
	}

}