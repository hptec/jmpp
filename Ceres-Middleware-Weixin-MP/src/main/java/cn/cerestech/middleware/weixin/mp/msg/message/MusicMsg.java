package cn.cerestech.middleware.weixin.mp.msg.message;

import cn.cerestech.middleware.weixin.mp.enums.MessageKey;
import cn.cerestech.middleware.weixin.mp.msg.Message;

/**
 * @author <a href="mailto:royrxc@gmail.com">Ranxc</a> 该Bean
 *         只用于消息回复，回复类型为music类型
 */
public class MusicMsg extends Message {
	public String title;
	public String description;
	/**
	 * 音乐链接 （非必须，但是和高质量链接必须至少有一项不为空）
	 */
	public String musicURL;
	/**
	 * 高质量音乐文件URL地址 （非必须，但是和音乐链接必须至少有一项不为空）
	 */
	public String HQMusicUrl;
	/**
	 * 必须项，不能为空，音乐缩略图ID，上传到微信服务器上的缩略图ID
	 */
	public String thumbMediaId;

	/**
	 * 
	 */
	public MusicMsg() {
		super();
		this.setMsgType(MessageKey.MUSIC);
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
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

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	protected String getIndividualXml() {
		StringBuffer xml = new StringBuffer();
		xml.append("<Music>");
		xml.append("<Title><![CDATA[" + getTitle() + "]]></Title>");
		xml.append("<Description><![CDATA[" + getDescription() + "]]></Description>");
		xml.append("<MusicUrl><![CDATA[" + getMusicURL() + "]]></MusicUrl>");
		xml.append("<HQMusicUrl><![CDATA[" + getHQMusicUrl() + "]]></HQMusicUrl>");
		xml.append("<ThumbMediaId><![CDATA[" + getThumbMediaId() + "]]></ThumbMediaId>");
		xml.append("</Music>");
		return xml.toString();
	}

}
