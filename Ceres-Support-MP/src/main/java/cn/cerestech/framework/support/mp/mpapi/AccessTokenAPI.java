package cn.cerestech.framework.support.mp.mpapi;

import com.google.common.primitives.Longs;

import cn.cerestech.framework.core.date.Dates;
import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.support.mp.entity.base.MpServerToken;
import cn.cerestech.framework.support.mp.entity.base.Status;

/**
 * 微信端ACCESS_TOKEN凭证
 * @author bird
 */
public class AccessTokenAPI extends API{
	private AccessTokenAPI(String appid, String appsecret) {
		super(appid, appsecret);
	}
	
	public static AccessTokenAPI of(String appid, String appsecret){
		return new AccessTokenAPI(appid, appsecret);
	}
	
	public Status<MpServerToken> accessToken(){
		StringBuffer url = new StringBuffer("https://api.weixin.qq.com/cgi-bin/token?");
		url.append("grant_type=client_credential");
		url.append("&appid=" + getAppid());
		url.append("&secret=" + getAppsecret());

		String content = Https.of().post(url.toString()).readString();
		
		Status<MpServerToken> status = Status.as(content);
		if(status.isSuccess()){
			Long et = Longs.tryParse(status.jsonValue("expires_in"));
			et = et==null?0L:et;
			et = Dates.now().toDate().getTime()+et - 1000*100;//实际时间减少100秒，保证有效
			status.setObject(MpServerToken.of(getAppid(), getAppsecret(), status.jsonValue("access_token"), et));
		}
		return status;
	}
	
}
