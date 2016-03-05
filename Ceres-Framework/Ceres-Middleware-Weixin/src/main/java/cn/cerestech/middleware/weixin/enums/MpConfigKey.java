package cn.cerestech.middleware.weixin.enums;

import cn.cerestech.framework.support.configuration.enums.ConfigKey;
/**
 * 微信公众号相关配置KEY
 * @author bird
 *
 */
public enum MpConfigKey implements ConfigKey {
	APPID("WECHAT_APPID", "微信公众号appid", ""), 
	APPSECRET("WECHAT_APPSECRET", "微信公众号appsecret",""), 
	WXGATETOKEN("WECHAT_WXGATETOKEN", "微信交互凭证", "");
	
	private String key, desc, defaultVal;

	private MpConfigKey(String key, String desc, String defaultVal) {
		this.key = key;
		this.desc = desc;
		this.defaultVal = defaultVal;
	}

	@Override
	public String key() {
		return this.key;
	}

	@Override
	public String desc() {
		return this.desc;
	}

	@Override
	public String defaultValue() {
		return this.defaultVal;
	}

	public static MpConfigKey keyOf(String key) {
		for (MpConfigKey item : MpConfigKey.values()) {
			if (item.key().equalsIgnoreCase(key)) {
				return item;
			}
		}
		return null;
	}
}
