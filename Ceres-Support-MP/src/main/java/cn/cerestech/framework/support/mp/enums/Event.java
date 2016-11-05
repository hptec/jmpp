package cn.cerestech.framework.support.mp.enums;

import com.google.common.base.Strings;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年10月29日
 */
public enum Event {
	/**
	 * 关注
	 */
	SUBSCRIBE("SUBSCRIBE","SUBSCRIBE"),
	/**
	 * 取消关注
	 */
	UNSUBSCRIBE("UNSUBSCRIBE","UNSUBSCRIBE"),
	/**
	 * 扫描关注二维码
	 */
	SCAN("SCAN","SCAN"),
	/**
	 * 自动上报地理位置
	 */
	LOCATION("LOCATION","LOCATION"),
	/**
	 * 点击自定义菜单
	 */
	CLICK("CLICK","CLICK"),
	/**
	 * 访问菜单连接
	 */
	VIEW("VIEW","VIEW"),
	/**
	 * 模板消息发送成功
	 */
	TEMPLATESENDJOBFINISH("TEMPLATESENDJOBFINISH","TEMPLATESENDJOBFINISH"),
	;
	private String key, desc;
	private Event(String key, String desc){
		this.key = key;
		this.desc = desc;
	}
	
	public String key(){
		return this.key;
	}
	public String desc(){
		return this.desc;
	}
	
	public static Event keyOf(String key){
		key = Strings.nullToEmpty(key);
		for (Event e : Event.values()) {
			if(key.contentEquals(e.key())){
				return e;
			}
		}
		return null;
	}
}
