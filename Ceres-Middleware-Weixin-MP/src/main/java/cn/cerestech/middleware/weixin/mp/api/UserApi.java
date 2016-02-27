package cn.cerestech.middleware.weixin.mp.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.entity.InputStreamEntity;
import org.apache.tools.ant.filters.StringInputStream;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import cn.cerestech.framework.core.Core;
import cn.cerestech.framework.core.HttpUtils;
import cn.cerestech.framework.core.Logable;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.middleware.weixin.entity.MpUser;
import cn.cerestech.middleware.weixin.entity.Status;
import cn.cerestech.middleware.weixin.mp.entity.UserToken;
import cn.cerestech.middleware.weixin.mp.enums.AuthorizeScope;

public class UserApi implements Logable {

	private MpApi parent;

	public UserApi(MpApi mpApi) {
		parent = mpApi;
	}

	/**
	 * 获取已经关注用户的个人信息
	 * 
	 * @param appid
	 * @param appsecret
	 * @param openid
	 * @return
	 */
	public Status<MpUser> get(String openid) {
		return parent.execute(token -> {
			StringBuffer url = new StringBuffer();
			url.append("https://api.weixin.qq.com/cgi-bin/user/info?");
			url.append("access_token=" + token);
			url.append("&openid=" + openid);
			url.append("&lang=zh_CN");
			
			log.trace("MpApi Request: [Get UserInfo] " + url.toString());
			String res = HttpUtils.post(url.toString());
			log.trace("MpApi Response:\n" + Jsons.toPrettyJson(res));
			
			Status<MpUser> statusReturn = Status.as(res);
			if (statusReturn.isSuccess()) {
				MpUser user = Jsons.fromJson(res, MpUser.class);
				statusReturn.setObject(user);
			}
			return statusReturn;
		});

	}

	/**
	 * 批量拉取用户信息，已经关注的用户才可以
	 * 
	 * @param appid
	 * @param appsecret
	 * @return
	 */
	public Status<List<MpUser>> batchGet(List<String> openids) {
		if (openids == null || openids.isEmpty() || openids.size() > 100) {
			throw new IllegalArgumentException("获取粉丝数量太多，不能超过100");
		}
		return parent.execute(token -> {
			StringBuffer url = new StringBuffer("https://api.weixin.qq.com/cgi-bin/user/info/batchget?");
			url.append("access_token=" + token);

			StringBuffer sb = new StringBuffer("");
			sb.append("{");
			sb.append("\"user_list\": [");
			openids.forEach(oid -> {
				sb.append("{");
				sb.append("\"openid\":").append("\"").append(oid).append("\"");
				sb.append("\"lang\":").append("\"").append("zh-CN").append("\"");
				sb.append("}");
			});
			sb.append("]}");

			String ret = HttpUtils.post(url.toString(), new InputStreamEntity(new StringInputStream(sb.toString())));
			Status<List<MpUser>> statusReturn = Status.as(ret);
			if (statusReturn.isSuccess()) {
				List<MpUser> retList = toMpUserList(statusReturn);
				statusReturn.setObject(retList);
			}
			return statusReturn;
		});

	}

	/**
	 * 根据infoList方法获取的结果，转换成MpUser 对象
	 * 
	 * @param infoList
	 * @return
	 */
	private static List<MpUser> toMpUserList(Status<List<MpUser>> infoList) {
		List<MpUser> retList = Lists.newArrayList();
		if (infoList.isSuccess()) {// 获取成功
			JsonObject fans = infoList.asJson().getAsJsonObject();
			if (fans.has("user_info_list")) {// 粉丝数据获取成功
				JsonArray arr = fans.get("user_info_list").getAsJsonArray();
				arr.forEach(info -> {
					String json = Jsons.toJson(info);
					MpUser u = Jsons.fromJson(json, MpUser.class);
					retList.add(u);
				});
			}
		}
		return retList;
	}

	/**
	 * 基本方式进行网页授权，只能获取OpenId，不能获取用户信息
	 * 
	 * @param redirect_uri
	 * @return
	 */
	public String authroizeBase(String redirect_uri) {
		StringBuffer url = new StringBuffer(Strings.nullToEmpty(parent.getProfile().getTarget_host()));
		if (url.toString().endsWith("/")) {
			url.deleteCharAt(url.length() - 1);
		}
		if (!redirect_uri.startsWith("/")) {
			url.append("/");
		}
		url.append(redirect_uri);

		return authorize(url.toString(), AuthorizeScope.SNSAPI_BASE, null);
	}

	/**
	 * 用户授权方式，可以直接获得OpenId和用户信息
	 * 
	 * @param redirect_uri
	 * @return
	 */
	public String authroizeUserInfo(String redirect_uri) {
		return authorize(redirect_uri, AuthorizeScope.SNSAPI_USERINFO, null);
	}

	private String authorize(String redirect_uri, AuthorizeScope scope, String state) {
		StringBuffer url = new StringBuffer();
		url.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
		url.append("appid=" + parent.getProfile().getAppid());
		try {
			url.append("&redirect_uri=" + URLEncoder.encode(redirect_uri, Core.charsetEncoding()));
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
	 * @param code
	 * @return
	 */
	public Status<UserToken> grantToken(String code) {

		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/sns/oauth2/access_token?");
		url.append("appid=" + parent.getProfile().getAppid());
		url.append("&secret=" + parent.getProfile().getSecret());
		url.append("&grant_type=authorization_code");
		url.append("&code=" + code);

		log.trace("MpApi Request: [Grant user access token] " + url.toString());
		String ret = HttpUtils.post(url.toString());
		log.trace("MpApi Response:\n" + Jsons.toPrettyJson(ret));

		Status<UserToken> retStatus = Status.as(ret);
		if (retStatus.isSuccess()) {
			UserToken token = Jsons.fromJson(ret, UserToken.class);
			retStatus.setObject(token);
		}

		return retStatus;
	}

	public Status<UserToken> refreshToken(String refresh_token) {
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/sns/oauth2/refresh_token?");
		url.append("appid=" + parent.getProfile().getAppid());
		url.append("&grant_type=refresh_token");
		url.append("&refresh_token=" + refresh_token);

		log.trace("MpApi Request: [Refresh user access token] " + url.toString());
		String ret = HttpUtils.post(url.toString());
		log.trace("MpApi Response:\n" + Jsons.toPrettyJson(ret));

		Status<UserToken> retStatus = Status.as(ret);
		if (retStatus.isSuccess()) {
			UserToken token = Jsons.fromJson(ret, UserToken.class);
			retStatus.setObject(token);
		}

		return retStatus;
	}

	public Status<MpUser> snsapiUserInfo(UserToken accessToken, String openId) {
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/sns/userinfo?");
		url.append("access_token=" + accessToken.getAccess_token());
		url.append("&openid=" + openId);
		url.append("&lang=zh_CN");

		log.trace("MpApi Request: [UserInfo get by snsapi_userinfo] " + url.toString());
		String ret = HttpUtils.post(url.toString());
		log.trace("MpApi Response:\n" + Jsons.toPrettyJson(ret));

		Status<MpUser> retStatus = Status.as(ret);
		if (retStatus.isSuccess()) {
			MpUser mpuser = Jsons.fromJson(ret, MpUser.class);
			retStatus.setObject(mpuser);
		}
		return retStatus;
	}
}
