package cn.cerestech.framework.support.mp.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

/**
 * 消息类型
 * 
 * @author harryhe
 *
 */
public enum MsgType implements DescribableEnum {
	TEXT("text", "文本"), //
	IMAGE("image", "图片"), //
	VOICE("voice", "语音"),//
	VIDEO("video","视频"),//
	SHORTVIDEO("shortvideo","小视频"),//
	LOCATION("location","位置"),//
	LINK("link","链接"),//
	;
	private String key, desc;

	private MsgType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String key() {
		return this.key;
	}

	public String desc() {
		return this.desc;
	}
}
