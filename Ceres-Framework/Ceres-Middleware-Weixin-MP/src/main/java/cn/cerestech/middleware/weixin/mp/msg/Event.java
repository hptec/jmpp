package cn.cerestech.middleware.weixin.mp.msg;

import cn.cerestech.middleware.weixin.mp.enums.EventType;
import cn.cerestech.middleware.weixin.mp.enums.MessageType;

/**
 * 事件推送消息 基本类
 * 
 * @author <a href="mailto:royrxc@gmail.com">bird</a>
 */
public abstract class Event extends WeiXinMessage {
	/**
	 * 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
	 */
	protected EventType event;

	public EventType getEvent() {
		return event;
	}

	public void setEvent(EventType event) {
		this.event = event;
	}

	public Event() {
		super();
		setMessageType(MessageType.EVENT);
	}

}
