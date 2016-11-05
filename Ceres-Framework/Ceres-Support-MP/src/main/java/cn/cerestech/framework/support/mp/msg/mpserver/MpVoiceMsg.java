package cn.cerestech.framework.support.mp.msg.mpserver;

import cn.cerestech.framework.support.mp.msg.mpserver.comm.MpMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年10月31日
 */
public class MpVoiceMsg extends MpMsg {
	private String recognition;
	private String format;
	private String mediaId;
	public String getRecognition() {
		return recognition;
	}
	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
}
