package cn.cerestech.framework.support.mp.msg.mpserver.event;

import com.google.common.base.Strings;

import cn.cerestech.framework.support.mp.msg.mpserver.comm.MpEventMsg;

/**
 * 关注/取消关注/扫描关注二维码/带参数的关注二维码
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月3日
 */
public class MpSubscribeEvt extends MpEventMsg {
	private String eventKey;
	private String ticket;//扫描带参数二维码才拥有
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	/**
	 * 二维码参数，如果存在则有，否则返回空字符串
	 * @return
	 */
	public String param(){
		return Strings.nullToEmpty(this.getEventKey()).replaceAll("qrscene_", "");
	}
	
}
