package cn.cerestech.framework.support.mp.msg.mpserver;

import cn.cerestech.framework.support.mp.msg.mpserver.comm.MpMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年10月31日
 */
public class MpLinkMsg extends MpMsg {
	private String description;
	private String url;
	private String title;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
