package cn.cerestech.middleware.weixin.api.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class JsapiService {
	// @Autowired
	// MpExecuteService execute;
//	@Autowired
//	AccessTokenApiService cache;
//	@Autowired
//	MpConfigService mpConfig;

	/**
	 * 对当前url 页面的 jsapi 功能进行签名
	 * 
	 * @param appid
	 * @param jsapiTicket
	 * @param url
	 * @return
	 */
	public Map<String, String> sign(String url) {
		// String ticket = cache.jsapiTicket();
		// return JsApi.sign(mpConfig.appid(), ticket, url);
		return null;
	}
}
