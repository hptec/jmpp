package cn.cerestech.middleware.aliyunoss.enums;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.support.configuration.enums.ConfigKey;

public enum AliyunOssConfigKey implements ConfigKey {

	ALIYUN_OSS_OPEN("ALIYUN_OSS_OPEN", "N", "是否开启阿里云开放存储"), //
	ALIYUN_OSS_VISIT_PREFIX("ALIYUN_OSS_VISIT_PREFIX", "", "阿里云的访问路径"), //
	ALIYUN_OSS_ACCESSKEY_ID("ALIYUN_OSS_ACCESSKEY_ID", "", "阿里云OSS服务访问ID"), //
	ALIYUN_OSS_ACCESSKEY_SECRET("ALIYUN_OSS_ACCESSKEY_SECRET", "", "阿里云OSS服务访问密钥"), //
	ALIYUN_OSS_PROVIDER("ALIYUN_OSS_PROVIDER", "", "阿里云OSS服务所属地域"), //
	ALIYUN_OSS_BUCKET_NAME("ALIYUN_OSS_BUCKET_NAME", "", "阿里云OSS存储空间名称"),//
	//
	;

	private String key, defaultValue, desc;

	private AliyunOssConfigKey(String key, String defaultValue, String desc) {
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

	public static AliyunOssConfigKey keyOf(String key) {
		for (AliyunOssConfigKey s : AliyunOssConfigKey.values()) {
			if (s.key.equals(key)) {
				return s;
			}
		}
		return null;
	}

	public static List<KV> toList() {
		List<KV> kv = Lists.newArrayList();
		for (AliyunOssConfigKey s : AliyunOssConfigKey.values()) {
			kv.add(KV.on().put("key", s.key()).put("desc", s.desc()).put("value", s.defaultValue()));
		}
		return kv;
	}
}