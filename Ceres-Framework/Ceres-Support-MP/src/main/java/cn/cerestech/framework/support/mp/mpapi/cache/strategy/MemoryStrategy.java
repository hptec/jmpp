package cn.cerestech.framework.support.mp.mpapi.cache.strategy;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Maps;

import cn.cerestech.framework.support.mp.entity.base.MpJsApiTicket;
import cn.cerestech.framework.support.mp.entity.base.MpServerToken;
import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.mpapi.AccessTokenAPI;
import cn.cerestech.framework.support.mp.mpapi.JSAPI;
import cn.cerestech.framework.support.mp.mpapi.cache.Executor;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年5月25日
 */
public class MemoryStrategy extends Executor{
	Log log = LogFactory.getLog(MemoryStrategy.class);
	
	private final static  Map<String, MpServerToken> SERVER_ACCESS_TOKEN_POOL = Maps.newHashMap();
	private final static Map<String, MpJsApiTicket> JS_TICKET_POOL = Maps.newHashMap();
	
	@Override
	public synchronized MpServerToken getAccessToken(String appid, String appsecret){
		MpServerToken token = SERVER_ACCESS_TOKEN_POOL.get(appid);
		if(token == null || token.isExpired()){
			Status<MpServerToken> status = AccessTokenAPI.of(appid, appsecret).accessToken();
			if(status != null && status.isSuccess()){
				cacheAccessToken(status.getObject());
				token = status.getObject();
			}
		}
		return token;
	}
	@Override
	public synchronized MpJsApiTicket getJsTicket(){
		MpJsApiTicket ticket = JS_TICKET_POOL.get(getAppid());
		if(ticket == null || ticket.isExpired()){//过期或者不存在
			expiredJsTicket(getAppid());
			ticket = null;
			Status<MpJsApiTicket> status = execute(token->{
				Status<MpJsApiTicket> s = JSAPI.of(getAppid(), getAppsecret()).jsApiTicket(token.getToken());
				return s;
			});
			if(status.isSuccess() && status.getObject() != null){
				cacheJsTicket(status.getObject());
				ticket = status.getObject();
			}
		}
		return ticket;
	}
	
	public synchronized static void cacheAccessToken(MpServerToken token){
		if(token != null){
			SERVER_ACCESS_TOKEN_POOL.put(token.getAppid(), token);
		}
	}
	
	public synchronized static void cacheJsTicket(MpJsApiTicket ticket){
		if(ticket != null){
			JS_TICKET_POOL.put(ticket.getAppid(), ticket);
		}
	}
	
	/**
	 * 将ServerToken 设置为过期
	 * @param appid
	 */
	public synchronized void expiredServerToken(String appid){
		SERVER_ACCESS_TOKEN_POOL.remove(appid);
	}
	
	public synchronized void expiredJsTicket(String appid){
		JS_TICKET_POOL.remove(appid);
	}
	
	private MemoryStrategy(String appid, String appsecret){
		this.setAppid(appid);
		this.setAppsecret(appsecret);
	}
	
	public static MemoryStrategy of(String appid, String appsecret){
		return new MemoryStrategy(appid, appsecret);
	}

}
