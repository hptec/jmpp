package cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg;

import com.google.common.base.Strings;

import cn.cerestech.framework.support.mp.enums.SIMsgType;
import cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg.comm.MpServiceMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月4日
 */
public class MpServiceVideoMsg extends MpServiceMsg{
	private String media_id;
	private String title;
	private String thumb_media_id;
	private String description;
	public MpServiceVideoMsg(){
		this.setMsgType(SIMsgType.VIDEO);
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumb_media_id() {
		return thumb_media_id;
	}
	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	protected String replyContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("\"video\":{\"media_id\":\"").append(this.getMedia_id()).append("\",");
		sb.append("\"thumb_media_id\":\"").append(Strings.nullToEmpty(this.getThumb_media_id())).append("\",");
		sb.append("\"title\":\"").append(this.getTitle()).append("\",");
		sb.append("\"description\":\"").append(Strings.nullToEmpty(this.getDescription())).append("\"}");
		return sb.toString();
	}
}
