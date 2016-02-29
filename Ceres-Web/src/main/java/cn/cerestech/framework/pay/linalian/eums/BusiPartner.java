package cn.cerestech.framework.pay.linalian.eums;

public enum BusiPartner {
	FACTITIOUS("101001","虚拟商品销售"),
	PHYSICAL("109001","实物商品销售");
	private String key, desc;
	private BusiPartner(String key, String desc){
		this.key = key;
		this.desc = desc;
	}
	public String key(){
		return this.key;
	}
	public String desc(){
		return this.desc;
	}
	
	public static BusiPartner keyOf(String key){
		for (BusiPartner item : BusiPartner.values()) {
			if(item.key().equals(key)){
				return item;
			}
		}
		return null;
	}
}
