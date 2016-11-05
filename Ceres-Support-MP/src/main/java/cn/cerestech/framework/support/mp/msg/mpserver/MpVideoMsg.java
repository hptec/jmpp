package cn.cerestech.framework.support.mp.msg.mpserver;

import cn.cerestech.framework.support.mp.msg.mpserver.comm.MpMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年10月31日
 */
public class MpVideoMsg extends MpMsg {
	private String thumbMediaId,mediaId;

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
}
