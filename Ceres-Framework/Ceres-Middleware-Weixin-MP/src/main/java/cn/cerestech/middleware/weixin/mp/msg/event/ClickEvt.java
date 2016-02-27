package cn.cerestech.middleware.weixin.mp.msg.event;

import cn.cerestech.middleware.weixin.mp.enums.EventType;
import cn.cerestech.middleware.weixin.mp.msg.Event;

public class ClickEvt extends Event {
	/**
	 * 事件KEY值，与自定义菜单接口中KEY值对应
	 */
	private String eventKey;

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public ClickEvt() {
		super();
		setEvent(EventType.CLICK);
	}

}
