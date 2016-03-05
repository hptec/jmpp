package cn.cerestech.middleware.weixin.mp.msg.message;

import cn.cerestech.middleware.weixin.mp.enums.MessageKey;
import cn.cerestech.middleware.weixin.mp.msg.Message;

public class VideoMsg extends Message {
	/**
	 * 视频消息媒体id，可以调用多媒体文件下载接口拉取数据
	 */
	private String mediaId;
	/**
	 * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据 (接收数据时使用)
	 */
	private String thumbMediaId;

	/**
	 * 回复时的视频标题
	 */
	private String title;

	/**
	 * 回复时的视频描述
	 */
	private String description;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
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

	public VideoMsg() {
		super();
		setMsgType(MessageKey.VIDEO);
	}

	@Override
	protected String getIndividualXml() {
		StringBuffer xml = new StringBuffer();
		xml.append("<Video>");
		xml.append("<MediaId><![CDATA[" + getMediaId() + "]]></MediaId>");
		xml.append("<Title><![CDATA[" + getTitle() + "]]></Title>");
		xml.append("<Description><![CDATA[" + getDescription() + "]]></Description>");
		xml.append("</Video>");
		return xml.toString();
	}
}
