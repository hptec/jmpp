package cn.cerestech.framework.support.mp.msg.client.passive;

import com.google.common.base.Strings;

import cn.cerestech.framework.support.mp.enums.SMsgType;
import cn.cerestech.framework.support.mp.msg.client.passive.comm.MpPassiveMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月3日
 */
public class MpPassiveTextMsg extends MpPassiveMsg {
	private String content;
	public MpPassiveTextMsg(){
		this.setMsgType(SMsgType.TEXT);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String replyContent() {
		return "<Content><![CDATA[" + Strings.nullToEmpty(content) + "]]></Content>";
	}
}
