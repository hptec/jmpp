package cn.cerestech.framework.support.mp.entity.base;

import com.google.common.base.Strings;

import cn.cerestech.framework.support.mp.mpapi.API;

public class MpServerToken extends API{
	private String token;
	private long expired_time;
	
	private MpServerToken(String appid, String appsecret,String token, long expired_time) {
		super(appid, appsecret);
		this.token = token;
		this.expired_time = expired_time;
	}
	
	public static MpServerToken of(String appid, String appsecret, String token, long expired_time){
		return new MpServerToken(appid, appsecret, token, expired_time);
	}

	public String getToken() {
		return token;
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
