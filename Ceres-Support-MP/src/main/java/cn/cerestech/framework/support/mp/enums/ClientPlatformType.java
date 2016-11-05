package cn.cerestech.framework.support.mp.enums;
/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年4月22日
 */
public enum ClientPlatformType {
	IOS("1","IOS"),
	ANDROID("2","Android"),
	OTHER("3", "Other")
	;
	private String key, desc;
	private ClientPlatformType(String key, String desc){
		this.key = key;
		this.desc = desc;
	}
	
	public String key(){
		return this.key;
	}
	public String desc(){
		return this.desc;
	}
}
