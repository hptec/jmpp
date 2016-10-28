package cn.cerestech.framework.support.mp.listener;

import cn.cerestech.framework.support.mp.msg.MpMsg;
import cn.cerestech.framework.support.mp.msg.event.LocationEvent;
import cn.cerestech.framework.support.mp.msg.event.MenuEvent;
import cn.cerestech.framework.support.mp.msg.event.SubscribeEvent;

public interface EventListener {

	/**
	 * 用户未关注扫码
	 * 
	 * @param msg
	 * @return
	 */
	public MpMsg onSubscribe(SubscribeEvent e);

	/**
	 * 用户已关注扫码
	 * 
	 * @param e
	 * @return
	 */
	public MpMsg onScan(SubscribeEvent e);

	/**
	 * 每5秒上传一次位置信息。
	 * 
	 * @param e
	 * @return
	 */
	public MpMsg onLocation(LocationEvent e);

	/**
	 * 菜单点击事件
	 * 
	 * @param e
	 * @return
	 */
	public MpMsg onMenuClick(MenuEvent e);
	
	/**
	 * 菜单打开连接事件
	 * @param e
	 * @return
	 */
	public MpMsg onMenuView(MenuEvent e);
	
	
}
