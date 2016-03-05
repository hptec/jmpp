package cn.cerestech.framework.pay.wxpay.enums;

public enum FeeType {
	CNY("CNY","人民币"),USD("USD","美元");
	private String key,desc;
	
	private FeeType(String key, String desc){
		this.key = key;
		this.desc = desc;
	}
	
	public static FeeType keyOf(String key){
		for (FeeType itm : FeeType.values()) {
			if(itm.key.equalsIgnoreCase(key)){
				return itm;
			}
		}
		return CNY;
	}
	
	public String key(){
		return this.key;
	}
	public String desc(){
		return this.desc;
	}
}
