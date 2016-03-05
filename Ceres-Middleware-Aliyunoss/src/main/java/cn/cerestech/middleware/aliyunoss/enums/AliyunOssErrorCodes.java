package cn.cerestech.middleware.aliyunoss.enums;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.enums.DescribableEnum;

public enum AliyunOssErrorCodes implements DescribableEnum {
	PROVIDER_NOT_EXIST("PROVIDER_NOT_EXIST", "找不到服务站点"), //
	;
	private String key, desc;

	private AliyunOssErrorCodes(String key, String desc) {
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

	public static AliyunOssErrorCodes keyOf(String key) {
		for (AliyunOssErrorCodes s : AliyunOssErrorCodes.values()) {
			if (s.key.equalsIgnoreCase(key)) {
				return s;
			}
		}
		return null;
	}

	public static List<KV> toList() {
		List<KV> kv = Lists.newArrayList();
		for (AliyunOssErrorCodes s : AliyunOssErrorCodes.values()) {
			kv.add(KV.on().put("key", s.key()).put("desc", s.desc()));
		}
		return kv;
	}
}