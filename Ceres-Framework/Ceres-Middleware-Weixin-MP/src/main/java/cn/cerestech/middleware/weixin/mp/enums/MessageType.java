package cn.cerestech.middleware.weixin.mp.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum MessageType implements DescribableEnum {
	MESSAGE("MESSAGE", "普通消息"), //
	EVENT("EVENT", "事件"), //
	;
	private String key, desc;

	private MessageType(String key, String desc) {
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
