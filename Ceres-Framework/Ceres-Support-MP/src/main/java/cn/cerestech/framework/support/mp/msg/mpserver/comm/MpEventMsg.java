package cn.cerestech.framework.support.mp.msg.mpserver.comm;

import cn.cerestech.framework.support.mp.enums.Event;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年10月31日
 */
public class MpEventMsg extends MpMsg {
	private Event event;

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
}
