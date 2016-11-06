package cn.cerestech.framework.support.mp.mpapi.ext;

import cn.cerestech.framework.support.mp.mpapi.OauthAPI;
import cn.cerestech.framework.support.mp.mpapi.cache.Executor;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年5月25日
 */
public class OAUTH extends OauthAPI{
	private Executor exec;
	public OAUTH(Executor exec){
		super(exec.getAppid(), exec.getAppsecret());
		this.exec = exec;
	}
	
	
	
	
}
