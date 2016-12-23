package cn.cerestech.framework.support.pay.wxpay.enums;

public enum TradeType {
	JSAPI("JSAPI","微信浏览器中JS 发起支付"),NATIVE("NATIVE","原生支付"),APP("APP","APP支付"),WAP("WAP","WAP支付"),OTHER("OTHER","其他类型");
	
	private String key,desc;
	
	private TradeType(String key, String desc){
		this.key = key;
		this.desc = desc;
	}
	
	public static TradeType keyOf(String key){
		for (TradeType itm : TradeType.values()) {
			if(itm.key.equalsIgnoreCase(key)){
				return itm;
			}
		}
		return OTHER;
	}
	
	public String key(){
		return this.key;
	}
	public String desc(){
		return this.desc;
	}
}
