package cn.cerestech.middleware.weixin.mp.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum AuthorizeScope implements DescribableEnum {
	SNSAPI_BASE("snsapi_base", "基本授权"), //
	SNSAPI_USERINFO("snsapi_userinfo", "授权用户信息"), //
	;
	private String key, desc;

	private AuthorizeScope(String key, String desc) {
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
