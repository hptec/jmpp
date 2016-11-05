package cn.cerestech.framework.support.mp.mpapi;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.google.common.primitives.Longs;

import cn.cerestech.framework.core.date.Dates;
import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.core.utils.Random;
import cn.cerestech.framework.core.utils.Utils;
import cn.cerestech.framework.support.mp.entity.base.MpJsApiTicket;
import cn.cerestech.framework.support.mp.entity.base.Status;

public class JSAPI extends API{
	
	public JSAPI(String appid, String appsecret){
		super(appid, appsecret);
	}
	
	public static JSAPI of(String appid, String appsecret){
		return new JSAPI(appid, appsecret);
	}
	
	public Map<String, String> signUrl(String jsapi_ticket, String url){
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = Random.chars(16);
		String timestamp = (Dates.now().toDate().getTime()/1000)+"";
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = Utils.byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("appid", getAppid());

		return ret;
	}
	
	/**
	 * 网页JS授权凭证jsapi_ticket
	 * @param server_token
	 * @return
	 * {
			"errcode":0,
			"errmsg":"ok",
			"ticket":"bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA",
			"expires_in":7200
		}
	 */
	public Status<MpJsApiTicket> jsApiTicket(String server_token){
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";//需要使用POST 提交
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("access_token", server_token);
		params.put("type", "jsapi");
		String content = Https.of().post(url, params).readString();
		Status<MpJsApiTicket> s = Status.as(content);
		
		if(s.isSuccess()){
			String ticket = s.jsonValue("ticket");
			Long expired = Longs.tryParse(s.jsonValue("expires_in"));
			expired = expired==null?0L:expired;
			s.setObject(MpJsApiTicket.of(getAppid(), getAppsecret(), ticket, expired));
		}
		return s;
	}
}