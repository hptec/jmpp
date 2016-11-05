package cn.cerestech.framework.support.mp.listener;

import cn.cerestech.framework.support.mp.msg.client.passive.comm.MpPassiveMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpImageMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpLinkMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpLocationMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpShortvideoMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpTextMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpVideoMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpVoiceMsg;

/**
 * 微信普通同类消息监听器
 * 
 * @author harryhe
 *
 */
public interface MessageListener {
	/**
	 * 接收到文本消息
	 * @param textMsg
	 * @return
	 */
	public MpPassiveMsg onText(MpTextMsg textMsg);

	/**
	 * 接收到图片消息
	 * @param imgMsg
	 * @return
	 */
	public MpPassiveMsg onImage(MpImageMsg imgMsg);
	
	/**
	 * 接收到 语音消息
	 * @param voiceMsg
	 * @return
	 */
	public MpPassiveMsg onVoice(MpVoiceMsg voiceMsg);
	/**
	 * 接收到视频消息
	 * @param videoMsg
	 * @return
	 */
	public MpPassiveMsg onVideo(MpVideoMsg videoMsg);
	/**
	 * 接收到短视频消息
	 * @param shortVideoMsg
	 * @return
	 */
	public MpPassiveMsg onShortVideo(MpShortvideoMsg shortVideoMsg);
	/**
	 * 接收到主动上报地理位置消息
	 * @param locMsg
	 * @return
	 */
	public MpPassiveMsg onLocation(MpLocationMsg locMsg);
	
	/**
	 * 接收到链接消息
	 * @param linkMsg
	 * @return
	 */
	public MpPassiveMsg onLink(MpLinkMsg linkMsg);

}
