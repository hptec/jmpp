package cn.cerestech.framework.pay.enums;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.KV;

public enum PayStatus {
	NEW("NEW", "新创建"), SUCCESS("SUCCESS", "完成"), FAIL("FAIL", "失败"), OTHER("OTHER", "UNKOWN");

	private String key, desc;

	private PayStatus(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String key() {
		return this.key;
	}

	public String desc() {
		return this.desc;
	}

	public static PayStatus keyOf(String key) {
		for (PayStatus item : PayStatus.values()) {
			if (item.key().equalsIgnoreCase(key)) {
				return item;
			}
		}
		return OTHER;
	}

	public static List<KV> toKVList() {
		List<KV> retList = Lists.newArrayList();
		for (PayStatus ps : values()) {
			retList.add(KV.on().put("key", ps.key()).put("desc", ps.desc()));
		}
		return retList;
	}

}
