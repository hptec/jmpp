package cn.cerestech.framework.support.mp.enums;

import cn.cerestech.framework.core.enums.Gender;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年4月22日
 */
public enum MpGender {
	M("1","男"),
	F("2","女"),
	U("3","未知");
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
	public static MpGender keyOf(String key){
		for (MpGender g : MpGender.values()) {
			if(g.key().equals(key)){
				return g;
			}
		}
		return U;
	}
	
	public Gender toGender(){
		switch (this) {
		case F:
			return Gender.FEMALE;
		case M:
			return Gender.MALE;
		default:
			return Gender.UNKNOWN;
		}
	}
}
