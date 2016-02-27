package cn.cerestech.framework.pay.enums;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.KV;

public enum PayChannel {
	ALIPAY("ALIPAY", "支付宝支付"), GOPAY("GOPAY", "国付宝支付"), JUBAOPAY("JUBAOPAY", "聚宝云计费"), WXPAY("WXPAY", "微信支付"),LLPAY("LLPAY","连连支付"), OTHER("OTHER", "其他支付");
	private String key, desc;

	private PayChannel(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String key() {
		return this.key;
	}

	public String desc() {
		return this.desc;
	}

	public static PayChannel keyOf(String key) {
		for (PayChannel channel : PayChannel.values()) {
			if (channel.key().equalsIgnoreCase(key)) {
				return channel;
			}
		}
		return OTHER;
	}

	public static List<KV> toKVList() {
		List<KV> retList = Lists.newArrayList();
		for (PayChannel ps : values()) {
			retList.add(KV.on().put("key", ps.key()).put("desc", ps.desc()));
		}
		return retList;
	}
}
