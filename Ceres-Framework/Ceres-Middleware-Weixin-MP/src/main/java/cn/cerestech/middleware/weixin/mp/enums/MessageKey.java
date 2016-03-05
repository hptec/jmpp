package cn.cerestech.middleware.weixin.mp.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum MessageKey implements DescribableEnum {
	/**
	 * 文本信息
	 */
	TEXT("text", "文本"),
	/**
	 * 图片信息
	 */
	IMAGE("image", "图片"),
	/**
	 * 语音信息
	 */
	VOICE("voice", "语音"),
	/**
	 * 视频信息
	 */
	VIDEO("video", "视频"),
	/**
	 * 短视频
	 */
	SHORTVIDEO("shortvideo", "短视频"),
	/**
	 * 地理位置信息
	 */
	LOCATION("location", "位置"),
	/**
	 * 链接信息
	 */
	LINK("link", "链接"),
	/**
	 * 音乐类型：回复消息使用类型
	 */
	MUSIC("music", "音乐"),
	/**
	 * 图文消息: 回复消息使用类型
	 */
	NEWS("news", "图文"),
	/**
	 * 客服消息，不做接收，只做回复，告诉微信是客服消息，需要转发给客服微信
	 */
	CUSTOMERMSG("transfer_customer_service", "客服"),
	/**
	 * 未知类型
	 */
	UNKNOWN("unknown", "未知");
	private String key, desc;

	private MessageKey(String key, String desc) {
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
