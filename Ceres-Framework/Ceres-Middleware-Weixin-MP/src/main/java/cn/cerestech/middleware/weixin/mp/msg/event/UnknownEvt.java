package cn.cerestech.middleware.weixin.mp.msg.event;

import cn.cerestech.middleware.weixin.mp.enums.EventType;
import cn.cerestech.middleware.weixin.mp.msg.Event;

public class UnknownEvt extends Event {

	public UnknownEvt() {
		super();
		setEvent(EventType.UNKNOW);
	}
}
