package cn.cerestech.middleware.weixin.mp.msg.message;

import cn.cerestech.middleware.weixin.mp.enums.MessageKey;
import cn.cerestech.middleware.weixin.mp.msg.Message;

/**
 * 语音消息
 * 
 * @author bird
 *
 */
public class VoiceMsg extends Message {
	/**
	 * 音频文件ID
	 */
	private String mediaId;
	/**
	 * 音频格式（接收信息时使用）
	 */
	private String format;
	/**
	 * 语音识别结果 消息发送时不进行转换 只有当开通了语音识别功能时才有
	 */
	private String recognition;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	public VoiceMsg() {
		super();
		setMsgType(MessageKey.VOICE);
	}

	@Override
	protected String getIndividualXml() {
		StringBuffer xml = new StringBuffer();
		xml.append("<Voice>");
		xml.append("<MediaId><![CDATA[" + getMediaId() + "]]></MediaId>");
		xml.append("</Voice>");
		return xml.toString();
	}

}
