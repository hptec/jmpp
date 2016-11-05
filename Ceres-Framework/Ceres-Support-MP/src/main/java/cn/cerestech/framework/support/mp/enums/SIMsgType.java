package cn.cerestech.framework.support.mp.enums;

import com.google.common.base.Strings;

/**
 * 客服消息类型
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月4日
 */
public enum SIMsgType {
	TEXT("text", "TEXT"), // 文本消息
	IMAGE("image", "IMAGE"), // 图片消息
	VOICE("voice", "VOICE"), // 语音消息
	VIDEO("video", "VIDEO"), // 视频消息
	MUSIC("music", "MUSIC"), // 音乐消息
	NEWS("news", "NEWS"), // 图文消息
	WXCARD("wxcard", "WXCARD"),// 微信卡券
	;
	private String key, desc;

	private SIMsgType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String key() {
		return this.key;
	}

	public String desc() {
		return this.desc;
	}

	public static SIMsgType keyOf(String key) {
		key = Strings.nullToEmpty(key);
		for (SIMsgType mt : SIMsgType.values()) {
			if (key.equalsIgnoreCase(mt.key())) {
				return mt;
			}
		}
		return null;
	}
}
