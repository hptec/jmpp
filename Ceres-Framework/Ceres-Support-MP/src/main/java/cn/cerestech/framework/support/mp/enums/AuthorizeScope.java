package cn.cerestech.framework.support.mp.enums;

public enum AuthorizeScope {
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
