package cn.cerestech.framework.support.mp.enums;

import com.google.common.base.Strings;

/**
 * 被动发送消息的类型
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月1日
 */
public enum SMsgType {
	TEXT("TEXT", "TEXT"), IMAGE("IMAGE", "IMAGE"), VOICE("VOICE", "VOICE"), VIDEO("VIDEO", "VIDEO"), MUSIC("MUSIC",
			"MUSIC"), NEWS("NEWS", "NEWS");
	private String key, desc;

	private SMsgType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String key() {
		return this.key;
	}

	public String desc() {
		return this.desc;
	}
	public static SMsgType keyOf(String key){
		key = Strings.nullToEmpty(key);
		for (SMsgType itm : SMsgType.values()) {
			if(key.equalsIgnoreCase(itm.key())){
				return itm;
			}
		}
		return null;
	}
}
