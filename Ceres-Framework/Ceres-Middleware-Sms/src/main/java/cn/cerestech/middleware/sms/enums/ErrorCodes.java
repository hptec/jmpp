package cn.cerestech.middleware.sms.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum ErrorCodes implements DescribableEnum {
	PHONE_ILLEGAL("PHONE_ILLEGAL", "电话号码非法"), //
	CONTENT_ILLEGAL("CONTENT_ILLEGAL", "内容不能为空或短信模板未找到"), //
	PHONE_BUSY("PHONE_BUSY", "该号码发送过于频繁"), //
	IP_BUSY("IP_BUSY", "IP发送过于频繁"), //
	SMS_PROVIDER_NOT_EXSIT("SMS_PROVIDER_NOT_EXSIT", "服务商不存在"), //
	SERVER_UNAVALABLE("SERVER_UNAVALABLE", "短信平台服务器不可用"), //
	SMS_NOT_OPEN("SMS_NOT_OPEN", "平台未开启短信功能"),//
	;
	private String key, desc;

	private ErrorCodes(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String key() {
		return key;
	}

	@Override
	public String desc() {
		return desc;
	}
}