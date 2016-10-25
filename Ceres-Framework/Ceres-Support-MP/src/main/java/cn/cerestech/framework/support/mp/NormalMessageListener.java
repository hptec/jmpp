package cn.cerestech.framework.support.mp;

import cn.cerestech.framework.support.mp.msg.MpMsg;
import cn.cerestech.framework.support.mp.msg.normal.ImageMsg;
import cn.cerestech.framework.support.mp.msg.normal.LinkMsg;
import cn.cerestech.framework.support.mp.msg.normal.LocationMsg;
import cn.cerestech.framework.support.mp.msg.normal.ShortVideoMsg;
import cn.cerestech.framework.support.mp.msg.normal.TextMsg;
import cn.cerestech.framework.support.mp.msg.normal.VideoMsg;
import cn.cerestech.framework.support.mp.msg.normal.VoiceMsg;

/**
 * 微信普通同类消息监听器
 * 
 * @author harryhe
 *
 */
public interface NormalMessageListener {

	public MpMsg onText(TextMsg textMsg);

	public MpMsg onImage(ImageMsg imgMsg);

	public MpMsg onVoice(VoiceMsg voiceMsg);

	public MpMsg onVideo(VideoMsg videoMsg);

	public MpMsg onShortVideo(ShortVideoMsg shortVideoMsg);

	public MpMsg onLocation(LocationMsg locMsg);

	public MpMsg onLink(LinkMsg linkMsg);

}
