package cn.cerestech.framework.web.front;

public enum Device {
	WAP("WAP","手机网页端"),WEB("WEB","电脑网页端"),
	OTHER("UNKNOW","未知类型");
	private String key, desc;
	private Device(String key, String desc){
		this.desc = desc;
		this.key = key;
	}
	public String key(){
		return this.key;
	}
	
	public String desc(){
		return this.desc;
	}
	
	public static Device keyOf(String key){
		for (Device dev : Device.values()) {
			if(dev.key().equalsIgnoreCase(key)){
				return dev;
			}
		}
		return OTHER;
	}
}
