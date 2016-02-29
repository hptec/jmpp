package cn.cerestech.middleware.weixin.mp.msg;

import cn.cerestech.middleware.weixin.mp.enums.MessageKey;
import cn.cerestech.middleware.weixin.mp.enums.MessageType;

/**
 * 微信基本消息 公共属性
 * 
 * @author <a href="mailto:royrxc@gmail.com">bird</a>
 *
 */
public abstract class Message extends WeiXinMessage {

	/**
	 * 消息类型
	 */
	protected MessageKey msgType;
	/**
	 * 当前消息ID,接收消息时用 接收事件消息时不带该参数
	 */
	protected String msgId;

	public MessageKey getMsgType() {
		return msgType;
	}

	public void setMsgType(MessageKey msgType) {
		this.msgType = msgType;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public Message() {
		super();
		setMessageType(MessageType.MESSAGE);
	}

}
