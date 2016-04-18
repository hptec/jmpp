package cn.cerestech.framework.platform.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.Random;
import cn.cerestech.framework.platform.dao.PlatformDao;
import cn.cerestech.framework.platform.entity.Platform;
import cn.cerestech.framework.platform.provider.PlatformIdentifyProvider;

@Service
public class PlatformService {

	private static Map<String, Platform> platformAuthMap = Maps.newHashMap();

	@Autowired
	PlatformDao platformDao;

	@Autowired()
	PlatformIdentifyProvider indentifyProvider;

	public Platform getPlatform() {
		return getIndentifyProvider().get();
	}

	public PlatformIdentifyProvider getIndentifyProvider() {
		return indentifyProvider;
	}

	public Long getId() {
		return getIndentifyProvider().getId();
	}

	/**
	 * 对信息进行认证，认证成功放入缓存
	 * 
	 * @param key
	 * @param secret
	 * @return
	 */
	public Platform getPlatform(String key, String secret) {
		Platform platform = platformDao.findUniqueByPlatformKeyAndPlatformSecret(key, secret);
		if (platform != null) {

			if (Strings.isNullOrEmpty(platform.getPlatformAuthCode())) {
				platform.setPlatformAuthCode(Random.uuid());
				platformDao.save(platform);
			}

			platformAuthMap.put(platform.getPlatformAuthCode(), platform);
		}

		return platform;
	}

	public Platform validate(String authCode) {
		Platform platform = null;
		if (platformAuthMap.containsKey(authCode)) {
			platform = platformAuthMap.get(authCode);
		} else {
			// 查看库中有没有
			platform = platformDao.findUniqueByPlatformAuthCode(authCode);
			if (platform != null) {
				platformAuthMap.put(authCode, platform);
			}
		}
		return platform;
	}

}
