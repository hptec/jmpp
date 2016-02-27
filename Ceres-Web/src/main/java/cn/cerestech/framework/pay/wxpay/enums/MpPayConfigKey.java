package cn.cerestech.framework.pay.wxpay.enums;

import cn.cerestech.framework.support.configuration.enums.ConfigKey;

public enum MpPayConfigKey implements ConfigKey{
	WECHAT_PAY_MCHNO("WECHAT_PAY_MCHNO", "微信支付的商户号", ""),// 微信支付的商户号
	WECHAT_PAY_PAYKEY("WECHAT_PAY_PAYKEY", "微信支付的支付秘钥", "");// 微信支付的支付秘钥
	
	private String key, desc, defaultValue;
	private MpPayConfigKey(String key, String desc, String defaultValue){
		this.key = key;
		this.desc = desc;
		this.defaultValue = defaultValue;
	}
	@Override
	public String key() {
		return this.key;
	}
	@Override
	public String defaultValue() {
		return this.defaultValue;
	}
	@Override
	public String desc() {
		return this.desc;
	}
	
	public static MpPayConfigKey keyOf(String key){
		for (MpPayConfigKey item : MpPayConfigKey.values()) {
			if(item.key().equalsIgnoreCase(key)){
				return item;
			}
		}
		return null;
	}
	
}