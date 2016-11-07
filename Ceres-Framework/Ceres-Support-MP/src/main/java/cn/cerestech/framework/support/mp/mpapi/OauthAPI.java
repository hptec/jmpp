package cn.cerestech.framework.support.mp.mpapi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.common.base.Strings;
import com.google.common.primitives.Longs;

import cn.cerestech.framework.core.environment.OsInfo;
import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.log.Logable;
import cn.cerestech.framework.support.mp.entity.base.MpUserGov;
import cn.cerestech.framework.support.mp.entity.base.MpUserToken;
import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.enums.AuthorizeScope;

public class OauthAPI extends API implements Logable{
	
	public static OauthAPI of(String appid, String appsecret){
		return new OauthAPI(appid, appsecret);
	}
	
	public OauthAPI(String appid, String appsecret) {
		super(appid, appsecret);
	}
	
	public String authURL(String redirect_uri){
		return this.authURL(redirect_uri, null, null);
	}
	
	public String authURL(String redirect_uri, AuthorizeScope scope, String state){
		scope = scope == null?AuthorizeScope.SNSAPI_BASE:scope;
		StringBuffer url = new StringBuffer();
		
		url.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
		url.append("appid=" + getAppid());
		try {
			url.append("&redirect_uri=" + URLEncoder.encode(redirect_uri, OsInfo.charsetEncoding()));
		} catch (UnsupportedEncodingException e) {
			log.catching(e);
		}
		url.append("&response_type=code");
		url.append("&scope=" + scope.key());
		if (!Strings.isNullOrEmpty(state)) {
			url.append("&state=" + state);
		}
		url.append("#wechat_redirect");
		return url.toString();
	}
	
	/**
	 * 通过code换取网页授权access_token
	 * 
	 * { 	"access_token":"ACCESS_TOKEN",    
	 * 		"expires_in":7200,    
	 *		"refresh_token":"REFRESH_TOKEN",    
	 *		"openid":"OPENID",    
	 *		"scope":"SCOPE" 
	 *	}
	 * @param code
	 * @return
	 */
	public Status<MpUserToken> grantToken(String code){
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/sns/oauth2/access_token?");
		url.append("appid=" + getAppid());
		url.append("&secret=" + getAppsecret());
		url.append("&grant_type=authorization_code");
		url.append("&code=" + code);

		String ret = Https.of().post(url.toString()).readString();

		Status<MpUserToken> retStatus = Status.as(ret);
		if (retStatus.isSuccess()) {
			String accesstoken  = retStatus.jsonValue("access_token");
			String refresh_token = retStatus.jsonValue("refresh_token");
			Long expired_time = Longs.tryParse(retStatus.jsonValue("expires_in"));
			String openid = retStatus.jsonValue("openid");
			expired_time = expired_time == null?0L:expired_time;
			
			String scope = retStatus.jsonValue("scope");
			MpUserToken token = MpUserToken.of(this.getAppid(), this.getAppsecret(), accesstoken, refresh_token, expired_time, openid, scope);
			retStatus.setObject(token);
		}
		return retStatus;
	}
	/**
	 * 刷新accesstoken 通过refresh_token
	 * @param refresh_token
	 * @return
	 * { "access_token":"ACCESS_TOKEN",  
		 "expires_in":7200,   
		 "refresh_token":"REFRESH_TOKEN",   
		 "openid":"OPENID",   
		 "scope":"SCOPE" }
	 */
	public Status<MpUserToken> flushToken(String refresh_token) {
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/sns/oauth2/refresh_token?");
		url.append("appid=" + getAppid());
		url.append("&grant_type=refresh_token");
		url.append("&refresh_token=" + refresh_token);

		String ret = Https.of().post(url.toString()).readString();

		Status<MpUserToken> retStatus = Status.as(ret);
		if (retStatus.isSuccess()) {
			String accesstoken  = retStatus.jsonValue("access_token");
			String rt = retStatus.jsonValue("refresh_token");
			Long expired_time = Longs.tryParse(retStatus.jsonValue("expires_in"));
			expired_time = expired_time == null?0L:expired_time;
			String openid = retStatus.jsonValue("openid");
			String scope = retStatus.jsonValue("scope");
			
			MpUserToken token = MpUserToken.of(this.getAppid(), this.getAppsecret(), accesstoken, rt, expired_time, openid, scope);
			retStatus.setObject(token);
		}

		return retStatus;
	}
	
	/**
	 * 返回access_token 是否有效
	 * @return
	 * { "errcode":0,"errmsg":"ok"} { "errcode":40003,"errmsg":"invalid openid"}
	 */
	public <T> Status<T> alive(String access_token, String openid){
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/sns/auth?");
		url.append("access_token=" + access_token);
		url.append("&openid=" + openid);

		String ret = Https.of().post(url.toString()).readString();
		Status<T> retStatus = Status.as(ret);
		return retStatus;
	}
	
	/**
	 * 拉取用户信息
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	public Status<MpUserGov> snsapiUserInfo(String accessToken, String openid) {
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/sns/userinfo?");
		url.append("access_token=" + accessToken);
		url.append("&openid=" + openid);
		url.append("&lang=zh_CN");

		String ret = Https.of().post(url.toString()).readString();

		Status<MpUserGov> retStatus = Status.as(ret);
		if (retStatus.isSuccess()) {
			MpUserGov mpuser = Jsons.from(ret).to(MpUserGov.class);
			retStatus.setObject(mpuser);
		}
		return retStatus;
	}
	
	/**
	 * 根据code 直接获取用户的信息
	 * @param code
	 * @return
	 */
	public Status<MpUserGov> snsapiUserInfo(String code){
		if(Strings.isNullOrEmpty(code)){
			return Status.ABSENT;
		}
		//1. 获取用户的拉去token
		Status<MpUserToken> ts = this.grantToken(code);
		if(ts.isSuccess()){
			String token = ts.getObject().getToken();
			//2. 根据token拉去相关信息
			System.out.println("拉去网页信息的scope："+ts.getObject().getScope());//snsapi_base
			AuthorizeScope as = AuthorizeScope.valueOf(ts.getObject().getScope());
			Status<MpUserGov> infoStatus = this.snsapiUserInfo(token, ts.getObject().getOpenid());
			if(!infoStatus.isSuccess()){
				MpUserGov mpuserGov = new MpUserGov();
				mpuserGov.setOpenid(ts.getObject().getOpenid());
				return new Status<MpUserGov>(0).setObject(mpuserGov);
			}
			return infoStatus;
		}
		return Status.ABSENT; 
	}
	
	
	
}
