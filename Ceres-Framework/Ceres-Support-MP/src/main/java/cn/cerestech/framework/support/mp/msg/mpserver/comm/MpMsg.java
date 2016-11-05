package cn.cerestech.framework.support.mp.msg.mpserver.comm;

import cn.cerestech.framework.support.mp.enums.RMsgType;

/**
 * 接收消息
 * 
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年10月29日
 */
public abstract class MpMsg {
	private RMsgType msgType;
	private String from;
	private String to;
	private Long createTime;
	private String msgId;

	public RMsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(RMsgType msgType) {
		this.msgType = msgType;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
}
