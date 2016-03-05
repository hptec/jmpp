package cn.cerestech.framework.pay.linalian.eums;

public enum AppReq {
	ANDROID("1", "ANDROID支付方式"),
	IOS("2","IOS支付方式"),
	WAP("3","WAP支付方式");
	
	private String key, desc;
	private AppReq(String key, String desc){
		this.key = key;
		this.desc = desc;
	}
	
	public String key(){
		return this.key;
	}
	public String desc(){
		return this.desc;
	}
	
	public static AppReq keyOf(String key){
		for (AppReq item : AppReq.values()) {
			if(item.key().equals(key)){
				return item;
			}
		}
		return null;
	}
}
