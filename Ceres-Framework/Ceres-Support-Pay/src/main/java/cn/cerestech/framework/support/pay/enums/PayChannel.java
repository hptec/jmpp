package cn.cerestech.framework.support.pay.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;


public enum PayChannel implements DescribableEnum{
	ALIPAY("ALIPAY", "支付宝支付"), 
	WXPAY("WXPAY", "微信支付");
	private String key, desc;

	private PayChannel(String key, String desc) {
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
	
	public static PayChannel keyOf(String key) {
		for (PayChannel channel : PayChannel.values()) {
			if (channel.key().equalsIgnoreCase(key)) {
				return channel;
			}
		}
		return null;
	}
}
