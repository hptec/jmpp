package cn.cerestech.middleware.weixin.mp.api;

import java.util.Map;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.cerestech.middleware.weixin.entity.Profile;
import cn.cerestech.middleware.weixin.entity.Status;
import cn.cerestech.middleware.weixin.enums.Code;

public class MpApi {

	protected static Map<String, ServerToken> tokenPool = Maps.newConcurrentMap();
	private static long lastCleanTime = 0L;// 上一次清理过期Token的时间
	protected Logger log = LogManager.getLogger();

	protected Profile profile;

	public JsApi JS;
	public MaterialApi MATERIAL;
	public MenuApi MENU;
	public ServerToken SERVERTOKEN;
	public UserApi USER;

	public static MpApi on(Profile p) {
		MpApi api = new MpApi(p);
		return api;
	}

	protected MpApi() {
		super();
	}

	protected MpApi(Profile p) {
		this.profile = p;
		JS = new JsApi(this);
		MATERIAL = new MaterialApi(this);
		MENU = new MenuApi(this);
		SERVERTOKEN = new ServerToken(this);
		USER = new UserApi(this);
	}

	protected Profile getProfile() {
		if (profile == null || Strings.isNullOrEmpty(profile.getAppid())
				|| Strings.isNullOrEmpty(profile.getSecret())) {
			throw new IllegalArgumentException("appid & appsecret is null");
		}

		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public <T> Status<T> execute(Function<String, Status<T>> api) {
		Status<T> s = api.apply(getAccessToken());
		if (s != null) {
			Code code = Code.valueOf(s.getCode());
			switch (code) {
			case ill_at:// access_token 已经过期
			case ill_at_timeout:// access_token 超时
				log.trace("MpApi access_token expired. regrant the token");
				SERVERTOKEN.grant();
				s = api.apply(getAccessToken());
				return s;
			default:
				return s;
			}
		} else {
			return s;
		}
	}

	private String getAccessToken() {
		if (System.currentTimeMillis() - lastCleanTime > 1 * 60 * 60 * 1000) {
			// 每小时清理一次
			tokenPool.keySet().stream().forEach(key -> {
				ServerToken t = tokenPool.get(key);
				if (t != null && t.isExpired()) {
					// 如果过期就移除
					tokenPool.remove(key);
				}
			});
			lastCleanTime = System.currentTimeMillis();
		}

		ServerToken token = tokenPool.get(getProfile().getAppid());
		if (token == null || token.isExpired()) {
			SERVERTOKEN.grant();// 重新获取访问令牌
			token = tokenPool.get(getProfile().getAppid());
		}

		return token.getToken();
	}

}
