package cn.cerestech.middleware.email.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;
/**
 * 邮件发送状态
 * @author <a href="mailto:royrxc@gmail.com">Roy</a>
 * @since  2015年11月21日上午12:05:12
 */
public enum EmailState implements DescribableEnum{
	WAIT("WAIT","等待发送"),
	SUCCESS("SUCCESS", "成功"),
	FAIL("FAIL","失败"),
	;
	private String key, desc;
	
	private EmailState(String key, String desc){
		this.key = key;
		this.desc = desc;
	}
	
	@Override
	public String desc() {
		return this.desc;
	}
	@Override
	public String key() {
		return this.key;
	}
	
	public static EmailState keyOf(String key){
		for (EmailState item : EmailState.values()) {
			if(item.key().equalsIgnoreCase(key)){
				return item;
			}
		}
		return null;
	}
}
