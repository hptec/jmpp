package com.cerestech.middleware.cert.enums;

public enum CertState {
	SUBMIT("SUBMIT", "新提交-待审核"),
	REJECT("REJECT","被驳回-审核未通过"),
	SUCCESS("SUCCESS","审核通过"),
	;
	
	private String key, desc;
	private CertState(String key, String desc){
		this.key = key;
		this.desc = desc;
	}
	
	public String key(){
		return this.key;
	}
	
	public String desc(){
		return this.desc;
	}
	
	public static CertState keyOf(String key){
		for (CertState item : CertState.values()) {
			if(item.key().equalsIgnoreCase(key)){
				return item;
			}
		}
		return null;
	}
}
