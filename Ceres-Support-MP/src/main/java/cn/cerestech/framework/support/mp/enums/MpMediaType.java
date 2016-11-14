package cn.cerestech.framework.support.mp.enums;
/**
 * 素材类型
 * @author bird
 */
public enum MpMediaType {
	 IMAGE("IMAGE","图片"), NEWS("NEWS","图文"), VOICE("VOICE","语音"),  VIDEO("VIDEO","视频"), THUMB("THUMB","缩略图");
	 private String key, desc;
	 private MpMediaType(String key , String desc){
		 this.key = key;
		 this.desc = desc;
	 }
	 
	 public String key(){
		 return this.key;
	 }
	 public String desc(){
		 return this.desc;
	 }
	 
	 public static MpMediaType keyOf(String key){
		 for (MpMediaType item : MpMediaType.values()) {
			if(item.key().equalsIgnoreCase(key)){
				return item;
			}
		}
		 return null;
	 }
}
