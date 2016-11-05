package cn.cerestech.framework.support.mp.listener;

import cn.cerestech.framework.support.mp.msg.client.passive.comm.MpPassiveMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpClickEvt;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpLocationEvt;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpSubscribeEvt;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpTempMsgStateEvt;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpViewEvt;

public interface EventListener {
	/**
	 * 用户取消关注
	 * @param e
	 */
	public void onUnSubscribe(MpSubscribeEvt e);

	/**
	 * 用户未关注扫码
	 * 
	 * @param msg
	 * @return
	 */
	public MpPassiveMsg onSubscribe(MpSubscribeEvt e);

	/**
	 * 用户已关注扫码
	 * 
	 * @param e
	 * @return
	 */
	public MpPassiveMsg onScan(MpSubscribeEvt e);

	/**
	 * 每5秒上传一次位置信息。
	 * 
	 * @param e
	 * @return
	 */
	public MpPassiveMsg onLocation(MpLocationEvt e);

	/**
	 * 菜单点击事件
	 * 
	 * @param e
	 * @return
	 */
	public MpPassiveMsg onMenuClick(MpClickEvt e);
	
	/**
	 * 菜单打开连接事件
	 * @param e
	 * @return
	 */
	public MpPassiveMsg onMenuView(MpViewEvt e);
	
	/**
	 * 模板消息发送状态
	 */
	public void onTemplateMsgState(MpTempMsgStateEvt e);
	
}
