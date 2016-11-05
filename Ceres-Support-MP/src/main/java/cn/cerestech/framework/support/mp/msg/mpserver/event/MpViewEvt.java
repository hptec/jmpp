package cn.cerestech.framework.support.mp.msg.mpserver.event;

import cn.cerestech.framework.support.mp.msg.mpserver.comm.MpEventMsg;

/**
 * 菜单定义的连接被访问
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月3日
 */
public class MpViewEvt extends MpEventMsg{
	private String eventKey;

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	
}
