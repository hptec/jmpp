package cn.cerestech.framework.support.mp.msg.client.passive;

import cn.cerestech.framework.support.mp.enums.SMsgType;
import cn.cerestech.framework.support.mp.msg.client.passive.comm.MpPassiveMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月3日
 */
public class MpPassiveVoiceMsg extends MpPassiveMsg {
	private String mediaId;
	
	public MpPassiveVoiceMsg(){
		this.setMsgType(SMsgType.VOICE);
	}
	
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	@Override
	public String replyContent() {
		StringBuffer xml = new StringBuffer();
		xml.append("<Voice>");
		xml.append("<MediaId><![CDATA[" + getMediaId() + "]]></MediaId>");
		xml.append("</Voice>");
		return xml.toString();
	}

}
