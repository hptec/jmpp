package cn.cerestech.framework.web.enums;

import cn.cerestech.framework.support.configuration.enums.ConfigKey;

public enum WebConfigKey implements ConfigKey {
	SITE_HOST("WEB_HOST", "", "站点域名，包括http头，结尾不包含/"), //
	SITE_LOGO("SITE_LOGO", "", "网站LOGO"), //
	SITE_NAME("SITE_NAME", "", "网站名称"), //
	SITE_SHORT_NAME("SITE_SHORT_NAME", "", "网站简称"), //
	SITE_TITLE("SITE_TITLE", "", "网站标题"), //
	SITE_KEYWORD("SITE_KEYWORD", "", "网站关键字"), //
	SITE_DESC("SITE_DESC", "", "网站描述"), //
	SITE_ICP("SITE_ICP", "", "网站备案号"), //
	SITE_IMG_BASE("SITE_IMG_BASE", "", "图片域名"), //
	SITE_CS_QQ("SITE_CS_QQ", "", "客服QQ"), //
	SITE_CS_PHONE("SITE_CS_PHONE", "", "客服电话"), //
	SITE_COPYRIGHT("SITE_COPYRIGHT", "", "版权信息"), //
	OUT_RES_PATH("OUT_RES_PATH", "", "外部资源域名前缀例如：oss");
	private String key, desc, defaultValue;

	private WebConfigKey(String key, String defaultValue, String desc) {
		this.key = key;
		this.desc = desc;
		this.defaultValue = defaultValue;
	}

	@Override
	public String key() {
		return this.key;
	}

	@Override
	public String defaultValue() {
		return this.defaultValue;
	}

	@Override
	public String desc() {
		return this.desc;
	}

	public static WebConfigKey keyOf(String key) {
		for (WebConfigKey item : WebConfigKey.values()) {
			if (item.key().equalsIgnoreCase(key)) {
				return item;
			}
		}
		return null;
	}

}
