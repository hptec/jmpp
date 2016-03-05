package cn.cerestech.middleware.weixin.mp.msg.message;

import cn.cerestech.middleware.weixin.mp.enums.MessageKey;
import cn.cerestech.middleware.weixin.mp.msg.Message;

public class ImageMsg extends Message {
	/**
	 * 图片地址（接受信息用） 发送信息时不使用
	 */
	private String picUrl;
	/**
	 * 图片系统ID
	 */
	private String mediaId;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public ImageMsg() {
		super();
		setMsgType(MessageKey.IMAGE);
	}

	@Override
	protected String getIndividualXml() {
		StringBuffer xml = new StringBuffer();
		xml.append("<Image>");
		xml.append("<MediaId><![CDATA[" + getMediaId() + "]]></MediaId>");
		xml.append("</Image>");
		return xml.toString();
	}

}
