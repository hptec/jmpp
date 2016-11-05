package cn.cerestech.framework.support.mp.msg.mpserver.event;

import cn.cerestech.framework.support.mp.msg.mpserver.comm.MpEventMsg;

/**
 *	菜单点击事件
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月3日
 */
public class MpClickEvt extends MpEventMsg {
	private String eventKey;

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	
}
