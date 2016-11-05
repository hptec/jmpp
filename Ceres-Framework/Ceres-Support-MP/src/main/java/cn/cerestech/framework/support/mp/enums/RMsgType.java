package cn.cerestech.framework.support.mp.enums;

import com.google.common.base.Strings;

/**
 * 接收消息类型
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月1日
 */
public enum RMsgType {
	/**
	 * 普通消息
	 */
	TEXT("TEXT", "TEXT"), IMAGE("IMAGE", "IMAGE"), VOICE("VOICE", "VOICE"), VIDEO("VIDEO", "VIDEO"), SHORTVIDEO(
			"SHORTVIDEO", "SHORTVIDEO"), LOCATION("SHORTVIDEO", "SHORTVIDEO"), LINK("LINK", "LINK"), 
	/**
	 * 事件
	 */
	EVENT("EVENT", "EVENT");
	private String key, desc;

	private RMsgType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String key() {
		return this.key;
	}

	public String desc() {
		return this.desc;
	}
	
	public static RMsgType keyOf(String key){
		key = Strings.nullToEmpty(key);
		for (RMsgType itm : RMsgType.values()) {
			if(key.equalsIgnoreCase(itm.key())){
				return itm;
			}
		}
		return null;
	}
}
