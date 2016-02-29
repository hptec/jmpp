package com.cerestech.middleware.cert.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum CertType implements DescribableEnum{
	IDCARD("IDCARD","身份证认证"),
	PASSPORT("PASSPORT", "护照认证"),
	QQ("QQ","QQ认证"),
	EMAIL("EMAIL","电子邮箱认证"),
	MOBILE("MOBILE","手机号码认证"),
	;
	
	private String key , desc;
	
	private CertType(String key, String desc){
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String key() {
		return this.key;
	}

	@Override
	public String desc() {
		return this.desc;
	}
	
	public static CertType keyOf(String key){
		for (CertType item : CertType.values()) {
			if(item.key().equalsIgnoreCase(key)){
				return item;
			}
		}
		return null;
	}
}
