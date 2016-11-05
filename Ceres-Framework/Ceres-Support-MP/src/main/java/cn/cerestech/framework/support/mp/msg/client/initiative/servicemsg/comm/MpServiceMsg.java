package cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg.comm;

import com.google.common.base.Strings;

import cn.cerestech.framework.support.mp.enums.SIMsgType;

/**
 * 主动发送的客服消息
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月4日
 */
public abstract class MpServiceMsg {
	private String toUser;
	private SIMsgType msgType;
	private String kf_account;
	
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public SIMsgType getMsgType() {
		return msgType;
	}
	public void setMsgType(SIMsgType msgType) {
		this.msgType = msgType;
	}
	
	public String getKf_account() {
		return kf_account;
	}
	public void setKf_account(String kf_account) {
		this.kf_account = kf_account;
	}
	protected abstract String replyContent();
	public String reply(){
		StringBuffer sb = new StringBuffer();
		sb.append("{\"touser\":\"").append(this.getToUser()).append("\",");
		sb.append("\"msgtype\":\"").append(this.getMsgType().key()).append("\",");
		if(!Strings.isNullOrEmpty(this.getKf_account())){
			sb.append("\"customservice\":{\"kf_account\": \"")
			.append(Strings.nullToEmpty(this.getKf_account())).append("\"},");
		}
		sb.append(this.replyContent());
		sb.append("}");
		return sb.toString();
	}

}
