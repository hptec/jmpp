package cn.cerestech.middleware.weixin.mp.api;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.middleware.weixin.WeiXinGateway;

public class JsApi {
	private static Logger log = LogManager.getLogger();

	private MpApi parent;

	public JsApi(MpApi mpApi) {
		parent = mpApi;
	}

	/**
	 * 签名页面jsapi 功能
	 * 
	 * @param appid
	 * @param jsapiTicket
	 * @param url
	 * @return
	 */
	public Map<String, String> sign(String jsapiTicket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = WeiXinGateway.create_nonce_str();
		String timestamp = WeiXinGateway.create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapiTicket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = WeiXinGateway.byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapiTicket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("appid", parent.getProfile().getAppid());

		log.trace("获取token ：" + Jsons.toJson(ret, false));
		return ret;
	}

}
