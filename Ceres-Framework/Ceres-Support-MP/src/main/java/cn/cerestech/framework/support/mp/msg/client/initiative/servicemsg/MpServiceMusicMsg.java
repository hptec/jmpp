package cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg;

import com.google.common.base.Strings;

import cn.cerestech.framework.support.mp.enums.SIMsgType;
import cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg.comm.MpServiceMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月4日
 */
public class MpServiceMusicMsg extends MpServiceMsg{
	
	private String title;
	private String description;
	private String musicurl;
	private String hgmusicurl;
	private String thumb_media_id;
	public MpServiceMusicMsg(){
		this.setMsgType(SIMsgType.MUSIC);
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
	public String getMusicurl() {
		return musicurl;
	}
	public void setMusicurl(String musicurl) {
		this.musicurl = musicurl;
	}
	public String getHgmusicurl() {
		return hgmusicurl;
	}
	public void setHgmusicurl(String hgmusicurl) {
		this.hgmusicurl = hgmusicurl;
	}
	public String getThumb_media_id() {
		return thumb_media_id;
	}
	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}
	@Override
	protected String replyContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("\"music\":")
		.append("{")
		.append("\"title\":\"").append(Strings.nullToEmpty(this.getTitle())).append("\",")
		.append("\"description\":\"").append(Strings.nullToEmpty(getDescription())).append("\",")
	    .append("\"musicurl\":\"").append(Strings.nullToEmpty(this.getMusicurl())).append("\",")
	    .append("\"hqmusicurl\":\"").append(Strings.nullToEmpty(this.getHgmusicurl())).append("\",")
	    .append("\"thumb_media_id\":\"").append(Strings.nullToEmpty(this.getThumb_media_id())).append("\"")
	    .append("}");
		return sb.toString();
	}
	
}
