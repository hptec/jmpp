package cn.cerestech.middleware.sms.enums;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.support.configuration.enums.ConfigKey;

public enum SmsConfigKeys implements ConfigKey {
	SMS_ENABLE("SMS_ENABLE", "Y", "短信功能是否开启"), SMS_DEFAULT_PROVIDER("SMS_DEFAULT_PROVIDER", "云片网络", "默认的短信平台");
	private String key, defaultValue, desc;

	private SmsConfigKeys(String key, String defaultValue, String desc) {
		this.key = key;
		this.defaultValue = defaultValue;
		this.desc = desc;
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

	public static SmsConfigKeys keyOf(String key) {
		for (SmsConfigKeys s : SmsConfigKeys.values()) {
			if (s.key.equalsIgnoreCase(key)) {
				return s;
			}
		}
		return null;
	}

	public static List<KV> toList() {
		List<KV> kv = Lists.newArrayList();
		for (SmsConfigKeys s : SmsConfigKeys.values()) {
			kv.add(KV.on().put("key", s.key()).put("desc", s.desc()).put("value", s.defaultValue()));
		}
		return kv;
	}

}