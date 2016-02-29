package cn.cerestech.middleware.email.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;
/**
 * 邮件类型
 * @author <a href="mailto:royrxc@gmail.com">Roy</a>
 * @since  2015年11月20日下午11:47:19
 */
public enum EmailType implements DescribableEnum{
	PLAN("PLAN","计划发送"),
	TIMELY("TIMELY","及时发送"),
	;
	private String key, desc;
	private EmailType(String key, String desc){
		this.key = key;
		this.desc = desc;
	}
	@Override
	public String key() {
		return this.key;
	}
	@Override
	public String desc() {
		return this.desc;
	}
	public static EmailType keyOf(String key){
		for (EmailType item : EmailType.values()) {
			if(item.key().equalsIgnoreCase(key)){
				return item;
			}
		}
		return null;
	}
}
