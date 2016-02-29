package cn.cerestech.framework.pay.jubao.enums;

public enum DefaultTab {
	ALIPAY("alipay","支付宝"),
	WXPAY("wxpay","微信支付"),
	TENPAY("tenpay","财付通支付");
	private String key, desc;
	
	private DefaultTab(String key, String desc){
		this.key = key;
		this.desc = desc;
	}
	
	public String key(){
		return this.key;
	}
	public String desc(){
		return this.desc;
	}
	
	public static DefaultTab keyOf(String key){
		for (DefaultTab item : DefaultTab.values()) {
			if(item.key().equalsIgnoreCase(key)){
				return item;
			}
		}
		return ALIPAY;
	}
}
