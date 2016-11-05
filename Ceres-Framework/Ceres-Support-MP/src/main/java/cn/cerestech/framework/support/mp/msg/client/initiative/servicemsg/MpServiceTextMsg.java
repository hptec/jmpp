package cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg;

import cn.cerestech.framework.support.mp.enums.SIMsgType;
import cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg.comm.MpServiceMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月4日
 */
public class MpServiceTextMsg extends MpServiceMsg{
	private String content;
	public MpServiceTextMsg(){
		this.setMsgType(SIMsgType.TEXT);
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	protected String replyContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("\"text\":{\"content\":\"").append(this.getContent()).append("\"}");
		return sb.toString();
	}
}
