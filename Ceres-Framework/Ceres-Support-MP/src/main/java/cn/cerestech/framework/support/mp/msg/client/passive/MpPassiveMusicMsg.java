package cn.cerestech.framework.support.mp.msg.client.passive;

import cn.cerestech.framework.support.mp.enums.SMsgType;
import cn.cerestech.framework.support.mp.msg.client.passive.comm.MpPassiveMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月3日
 */
public class MpPassiveMusicMsg extends MpPassiveMsg {
	
	private String title, description;
	private String musicURL;
	private String hQMusicUrl;
	private String thumbMediaId;
	
	public MpPassiveMusicMsg(){
		this.setMsgType(SMsgType.MUSIC);
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

	public String getMusicURL() {
		return musicURL;
	}

	public void setMusicURL(String musicURL) {
		this.musicURL = musicURL;
	}

	public String gethQMusicUrl() {
		return hQMusicUrl;
	}

	public void sethQMusicUrl(String hQMusicUrl) {
		this.hQMusicUrl = hQMusicUrl;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	public String replyContent() {
		StringBuffer xml = new StringBuffer();
		xml.append("<Music>");
		xml.append("<Title><![CDATA[" + getTitle() + "]]></Title>");
		xml.append("<Description><![CDATA[" + getDescription() + "]]></Description>");
		xml.append("<MusicUrl><![CDATA[" + getMusicURL() + "]]></MusicUrl>");
		xml.append("<HQMusicUrl><![CDATA[" + gethQMusicUrl() + "]]></HQMusicUrl>");
		xml.append("<ThumbMediaId><![CDATA[" + getThumbMediaId() + "]]></ThumbMediaId>");
		xml.append("</Music>");
		return xml.toString();
	}

}
