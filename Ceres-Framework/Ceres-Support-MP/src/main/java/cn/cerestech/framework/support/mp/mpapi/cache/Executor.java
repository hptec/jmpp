package cn.cerestech.framework.support.mp.mpapi.cache;

import java.util.function.Function;

import cn.cerestech.framework.support.mp.entity.base.MpServerToken;
import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.enums.Code;
import cn.cerestech.framework.support.mp.mpapi.ext.JS;
import cn.cerestech.framework.support.mp.mpapi.ext.MATERIALAPI;
import cn.cerestech.framework.support.mp.mpapi.ext.MENU;
import cn.cerestech.framework.support.mp.mpapi.ext.MENUSPEC;
import cn.cerestech.framework.support.mp.mpapi.ext.OAUTH;
import cn.cerestech.framework.support.mp.mpapi.ext.OTHERS;
import cn.cerestech.framework.support.mp.mpapi.ext.TMPMSGAPI;
import cn.cerestech.framework.support.mp.mpapi.ext.USERAPI;
import cn.cerestech.framework.support.mp.mpapi.ext.WAITERAPI;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年5月25日
 */
public abstract class Executor implements CacheApi{
	private String appid, appsecret;

	public <T> Status<T> execute(Function<MpServerToken, Status<T>> api) {
		Status<T> s = api.apply(getAccessToken(appid, appsecret));
		if (s != null) {
			Code code = Code.valueOf(s.getCode());
			switch (code) {
			case ill_at:// access_token 已经过期
			case ill_at_timeout:// access_token 超时
					expiredServerToken(appid);
					s = api.apply(getAccessToken(appid, appsecret));
					return s;
				default:
					return s;
			}
		} else {
			return s;
		}
	}
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public WAITERAPI WAITERAPI(){
		return new WAITERAPI(this);
	}
	
	public USERAPI USERAPI(){
		return new USERAPI(this);
	}
	public JS JS(){
		return new JS(this);
	}
	public MATERIALAPI MATERIAL(){
		return new MATERIALAPI(this);
	}
	public MENU MENU(){
		return new MENU(this);
	}
	public MENUSPEC MENUSPEC(){
		return new MENUSPEC(this);
	}
	public OTHERS OTHERS(){
		return new OTHERS(this);
	}
	public OAUTH OAUTH(){
		return new OAUTH(this);
	}
	public TMPMSGAPI TMPMSGAPI(){
		return new TMPMSGAPI(this);
	} 
	
}
