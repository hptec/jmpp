package cn.cerestech.framework.support.mp.entity.base;

import com.google.common.base.Strings;

import cn.cerestech.framework.support.mp.mpapi.API;

/**
 * 2.0认证的accesstoken
 * @author bird
 *
 */
public class MpUserToken extends API{
	private String token;
	private long expired_time;
	private String refresh_token;
	
	public MpUserToken(String appid, String appsecret) {
		super(appid, appsecret);
	}
	
	public static MpUserToken of(String appid, String appsecret, String token, String refresh_token, long expired_time){
		MpUserToken t = new MpUserToken(appid, appsecret);
		t.token = token;
		t.expired_time = expired_time;
		t.refresh_token = refresh_token;
		return t;
	}
	
	public String getToken() {
		return token;
	}
	
	public String getRefresh_token() {
		return refresh_token;
	}
	
	public boolean isExpired(){
		if (Strings.isNullOrEmpty(token)) {
			return Boolean.TRUE;
		} else {
			// 当前时间是否在过期时间之后
			return System.currentTimeMillis() > expired_time;
		}
	}
}
