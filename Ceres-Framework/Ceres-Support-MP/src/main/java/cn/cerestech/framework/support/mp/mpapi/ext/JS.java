package cn.cerestech.framework.support.mp.mpapi.ext;

import java.util.Map;

import cn.cerestech.framework.support.mp.entity.base.MpJsApiTicket;
import cn.cerestech.framework.support.mp.mpapi.JSAPI;
import cn.cerestech.framework.support.mp.mpapi.cache.Executor;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年5月25日
 */
public class JS extends JSAPI{
	private Executor exec;
	public JS(Executor exec){
		super(exec.getAppid(), exec.getAppsecret());
		this.exec = exec;
	}
	public Map<String, String> signUrl(String url){
		return signUrl(exec.getJsTicket().getTicket(), url);
	}
	
	public MpJsApiTicket jsApiTicket(){
		MpJsApiTicket ticket =  exec.getJsTicket();
		return ticket;
	}
}
