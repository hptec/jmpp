package cn.cerestech.middleware.sms.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum Providers implements DescribableEnum {
	YUNPIAN("云片网络", "云片网络"), //
	HUONI("火尼短信", "火尼短信"),//
	;

	private String key, desc;

	private Providers(String key, String desc) {
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
