package cn.cerestech.middleware.email.enums;

import cn.cerestech.framework.support.configuration.enums.ConfigKey;

/**
 * 邮件发送配置
 * @author <a href="mailto:royrxc@gmail.com">Roy</a>
 * @since  2015年11月22日下午9:00:27
 */
public enum EmailConfigKey implements ConfigKey{
	EMAIL_SMTP_SERVER("SMTP_SERVER","smtp.ym.163.com","发送邮件服务器地址"),
	EMAIL_SERVER_ACC("EMAIL_SERVER_ACC","service@hxb99.com","发件邮箱"),
	EMAIL_SERVER_PWD("EMAIL_SERVER_PWD", "111111", "发件邮箱账号登录密码"),
	;
	private String key, desc, defaultValue;
	private EmailConfigKey(String key, String defaultValue, String desc){
		this.key = key;
		this.defaultValue = defaultValue;
		this.desc = desc;
	}
	@Override
	public String defaultValue() {
		return this.defaultValue;
	}
	@Override
	public String key() {
		return this.key;
	}
	@Override
	public String desc() {
		return this.desc;
	}
	
	public static EmailConfigKey keyOf(String key){
		for (EmailConfigKey item : EmailConfigKey.values()) {
			if(item.key().equalsIgnoreCase(key)){
				return item;
			}
		}
		return null;
	}
}
