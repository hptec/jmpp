package cn.cerestech.framework.support.mp.msg.client.passive.comm;

import cn.cerestech.framework.support.mp.enums.SMsgType;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月3日
 */
public abstract class MpPassiveMsg {
	private String to;
	private String from;
	private Long createTime;
	private SMsgType msgType;
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public SMsgType getMsgType() {
		return msgType;
	}
	public void setMsgType(SMsgType msgType) {
		this.msgType = msgType;
	}
	public abstract String replyContent();
	
	public String reply(){
		StringBuffer msg = new StringBuffer();
		msg.append("<xml>");
		msg.append("<ToUserName><![CDATA[").append(this.getTo()).append("]]></ToUserName>");
		msg.append("<FromUserName><![CDATA[").append(this.getFrom()).append("]]></FromUserName>");
		msg.append("<CreateTime>").append(this.getCreateTime()==null?System.currentTimeMillis()/1000:this.getCreateTime()).append("</CreateTime>");
		msg.append("<MsgType><![CDATA[").append(this.getMsgType().key()).append("]]></MsgType>");
		msg.append(this.replyContent());
		msg.append("</xml>");
		return msg.toString();
	}
}
