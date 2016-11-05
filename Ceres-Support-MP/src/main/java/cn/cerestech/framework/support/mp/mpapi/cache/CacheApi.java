package cn.cerestech.framework.support.mp.mpapi.cache;

import cn.cerestech.framework.support.mp.entity.base.MpJsApiTicket;
import cn.cerestech.framework.support.mp.entity.base.MpServerToken;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年5月25日
 */
public interface CacheApi{
	public MpServerToken getAccessToken(String appid, String appsecret);
	/**
	 * 将ServerToken 设置为过期
	 * @param appid
	 */
	public void expiredServerToken(String appid);
	
	/**
	 * JSAPI ticket
	 * @return
	 */
	public MpJsApiTicket getJsTicket();
	/**
	 *  设置为过期
	 * @param appid
	 */
	public void expiredJsTicket(String appid);
}
