package cn.cerestech.framework.support.mp.msg.client.passive;

import cn.cerestech.framework.support.mp.enums.SMsgType;
import cn.cerestech.framework.support.mp.msg.client.passive.comm.MpPassiveMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月3日
 */
public class MpPassiveVideoMsg extends MpPassiveMsg {
	private String mediaId;
	private String title, description;

	public MpPassiveVideoMsg() {
		this.setMsgType(SMsgType.VIDEO);
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String replyContent() {
		StringBuffer xml = new StringBuffer();
		xml.append("<Video>");
		xml.append("<MediaId><![CDATA[" + getMediaId() + "]]></MediaId>");
		xml.append("<Title><![CDATA[" + getTitle() + "]]></Title>");
		xml.append("<Description><![CDATA[" + getDescription() + "]]></Description>");
		xml.append("</Video>");
		return xml.toString();
	}
}
