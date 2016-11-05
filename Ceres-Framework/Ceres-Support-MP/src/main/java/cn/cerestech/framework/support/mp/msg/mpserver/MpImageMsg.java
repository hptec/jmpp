package cn.cerestech.framework.support.mp.msg.mpserver;

import cn.cerestech.framework.support.mp.msg.mpserver.comm.MpMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年10月31日
 */
public class MpImageMsg extends MpMsg{
	private String picUrl;
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
	
}
