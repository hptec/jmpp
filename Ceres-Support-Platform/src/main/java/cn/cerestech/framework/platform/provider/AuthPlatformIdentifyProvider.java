package cn.cerestech.framework.platform.provider;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.Random;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.platform.dao.PlatformDao;
import cn.cerestech.framework.platform.entity.Platform;
import cn.cerestech.framework.platform.enums.ErrorCodes;
import cn.cerestech.framework.platform.interceptor.PlatformInterceptor;
import cn.cerestech.framework.support.web.WebUtils;

@Service
public class AuthPlatformIdentifyProvider implements PlatformIdentifyProvider {

	@Autowired
	PlatformDao platformDao;

	@Override
	public Platform get() {
		Long id = (Long) WebUtils.getCurrentRequest().getAttribute(PlatformInterceptor.COOKIE_CERES_PLATFORM_ID);
		if (id == null) {
			throw new IllegalArgumentException("platform key & secrect shoud be specified");
		} else {
			return platformDao.findOne(id);
		}
	}

	@Override
	public Result<Platform> authentication(String key, String secret) {
		Platform platform = platformDao.findByPlatformKey(key).stream().findFirst().orElse(null);
		if (platform == null) {
			// platform对象不存在
			return Result.error(ErrorCodes.PLATFORM_AUTH_INCORRECT);
		}

		if (!Strings.nullToEmpty(secret).equals(platform.getPlatformSecret())) {
			// 密码不匹配
			return Result.error(ErrorCodes.PLATFORM_AUTH_INCORRECT);
		}

		if (platform.getPlatformExpired() != null && new Date().after(platform.getPlatformExpired())) {
			// 过期时间为空则默认为永久授权
			return Result.error(ErrorCodes.PLATFORM_AUTH_EXPIRED);
		}

		return Result.success(platform);

	}

	public static void main(String[] arugs){
		System.out.println(Random.uuid());
		System.out.println(Random.uuid());
	}
}
