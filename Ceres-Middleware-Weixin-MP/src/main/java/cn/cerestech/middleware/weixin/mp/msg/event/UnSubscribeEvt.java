package cn.cerestech.middleware.weixin.mp.msg.event;

import cn.cerestech.middleware.weixin.mp.enums.EventType;
import cn.cerestech.middleware.weixin.mp.msg.Event;

public class UnSubscribeEvt extends Event {

	public UnSubscribeEvt() {
		super();
		setEvent(EventType.UNSUBSCRIBE);
	}
}
