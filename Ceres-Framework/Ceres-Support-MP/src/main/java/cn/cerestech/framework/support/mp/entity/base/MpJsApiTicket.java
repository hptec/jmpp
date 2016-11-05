package cn.cerestech.framework.support.mp.entity.base;

import com.google.common.base.Strings;

import cn.cerestech.framework.support.mp.mpapi.API;

public class MpJsApiTicket extends API{
	private String ticket;
	private long expired_time;
	
	private MpJsApiTicket(){super(null, null);}
	private MpJsApiTicket(String appid, String appsecret) {
		super(appid, appsecret);
	}
	
	public static MpJsApiTicket of(String appid, String appsecret, String ticket, long expired_time){
		MpJsApiTicket t = new MpJsApiTicket(appid, appsecret);
		t.expired_time = expired_time;
		t.ticket = ticket;
		return t;
	}
	
	public String getTicket() {
		return ticket;
	}
	
	public boolean isExpired(){
		if (Strings.isNullOrEmpty(ticket)) {
			return Boolean.TRUE;
		} else {
			// 当前时间是否在过期时间之后
			return System.currentTimeMillis() > expired_time;
		}
	}

}
