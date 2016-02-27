package cn.cerestech.middleware.weixin.mp.msg.event;

import cn.cerestech.middleware.weixin.mp.enums.EventType;
import cn.cerestech.middleware.weixin.mp.msg.Event;

/**
 * @author <a href="mailto:royrxc@gmail.com">Ranxc</a> scene：1. 用户未关注的关注 2.
 *         用户已经关注的扫描关注二维码 3. 用户取消关注
 */
public class SubscribeEvt extends Event {
	/**
	 * 当且仅当 扫描带有参数的二维码时候才会带上该参数 事件KEY值，qrscene_为前缀，后面为二维码的参数值
	 */
	private String eventKey;
	/**
	 * 当且仅当 扫描带有参数的二维码时候才会带上该参数 二维码的ticket，可用来换取二维码图片
	 */
	private String ticket;

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

	public SubscribeEvt() {
		super();
		setEvent(EventType.SUBSCRIBE);
	}

}
