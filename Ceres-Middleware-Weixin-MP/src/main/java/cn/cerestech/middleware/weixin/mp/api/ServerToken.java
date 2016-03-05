package cn.cerestech.middleware.weixin.mp.api;

import com.google.common.base.Strings;
import com.google.gson.JsonObject;

import cn.cerestech.framework.core.HttpUtils;
import cn.cerestech.framework.core.Logable;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.middleware.weixin.entity.Status;

public class ServerToken implements Logable {

	private String token;

	private long expiredTime = 0;

	private MpApi parent;

	public ServerToken(MpApi mpApi) {
		parent = mpApi;
	}

	public String getToken() {
		return this.token;
	}

	/**
	 * Token是否过期
	 * 
	 * @return
	 */
	public Boolean isExpired() {
		if (Strings.isNullOrEmpty(token)) {
			return Boolean.TRUE;
		} else {
			// 当前时间是否在过期时间之后
			return System.currentTimeMillis() > expiredTime;
		}
	}

	public Status<ServerToken> grant() {
		StringBuffer url = new StringBuffer("https://api.weixin.qq.com/cgi-bin/token?");
		url.append("grant_type=client_credential");
		url.append("&appid=" + parent.getProfile().getAppid());
		url.append("&secret=" + parent.getProfile().getSecret());

		log.trace("MpApi Request: [Grant access_token] " + url);

		String content = HttpUtils.post(url.toString());
		log.trace("MpApi Response \n" + Jsons.toPrettyJson(content));

		Status<ServerToken> status = Status.as(content);
		if (status.isSuccess()) {
			JsonObject json = status.asJson().getAsJsonObject();
			String token = json.get("access_token").getAsString();
			int expiresIn = json.get("expires_in").getAsInt();
			this.token = token;
			this.expiredTime = System.currentTimeMillis() + expiresIn * 1000;
			MpApi.tokenPool.put(parent.getProfile().getAppid(), this);
			return status.setObject(this);
		} else {
			log.warn(status.toJson());
			return Status.ABSENT;
		}
	}
}
