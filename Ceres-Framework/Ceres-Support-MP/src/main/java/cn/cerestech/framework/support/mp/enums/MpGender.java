package cn.cerestech.framework.support.mp.enums;
/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年4月22日
 */
public enum MpGender {
	M("1","男"),
	F("2","女");
	private String key, desc;
	private MpGender(String key, String  desc){
		this.key = key;
		this.desc = desc;
	}
	public String desc(){
		return this.desc;
	}
	public String key(){
		return this.key;
	}
}
