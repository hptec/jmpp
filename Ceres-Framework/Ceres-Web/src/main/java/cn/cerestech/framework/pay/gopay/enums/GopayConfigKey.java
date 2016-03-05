package cn.cerestech.framework.pay.gopay.enums;

import cn.cerestech.framework.support.configuration.enums.ConfigKey;

public enum GopayConfigKey  implements ConfigKey{
	GOPAY_MERCHANT_ID("GOPAY_MERCHANT_ID","国付宝商户号","0000003271"),
	GOPAY_MERCHANT_VERCODE("GOPAY_MERCHANT_VERCODE","国付宝商户识别码","YiQidUoBaOdATiAnXiA"),
	GOPAY_MERCHANT_TLJ_ACC("GOPAY_MERCHANT_TLJ_ACC","国付宝提留金账号","0000000002000001465");
	
	private String key, desc, defaultValue;
	private GopayConfigKey(String key, String desc, String defaultValue){
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
	
	public static GopayConfigKey keyOf(String key){
		for (GopayConfigKey item : GopayConfigKey.values()) {
			if(item.key().equalsIgnoreCase(key)){
				return item;
			}
		}
		return null;
	}

}
