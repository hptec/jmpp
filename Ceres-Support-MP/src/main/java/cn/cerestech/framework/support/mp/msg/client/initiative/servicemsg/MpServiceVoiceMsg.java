package cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg;

import cn.cerestech.framework.support.mp.enums.SIMsgType;
import cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg.comm.MpServiceMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月4日
 */
public class MpServiceVoiceMsg extends MpServiceMsg {
	private String media_id;
	public MpServiceVoiceMsg(){
		this.setMsgType(SIMsgType.VOICE);
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	@Override
	protected String replyContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("\"voice\":{\"media_id\":\"").append(this.getMedia_id()).append("\"}");
		return sb.toString();
	}
}
