package cn.cerestech.framework.platform.interceptor;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;

import cn.cerestech.framework.core.Random;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.platform.entity.Platform;
import cn.cerestech.framework.platform.enums.ErrorCodes;
import cn.cerestech.framework.platform.service.PlatformService;
import cn.cerestech.framework.support.web.Cookies;
import cn.cerestech.framework.support.web.WebSupport;

@Component
public class PlatformInterceptor extends WebSupport implements HandlerInterceptor {

	public static final String COOKIE_CERES_PLATFORM_KEY = "ceres_platform_key";
	public static final String COOKIE_CERES_PLATFORM_SECRET = "ceres_platform_secret";
	public static final String COOKIE_CERES_PLATFORM_ID = "COOKIE_CERES_PLATFORM_ID";

//	private Logger log = LogManager.getLogger();

	// token有效期2小时
	private Cache<String, Long> tokenPool = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.HOURS).build();

	private Set<String> excludeUris = Sets.newHashSet(//
			"/api/classpath/query/support/web/js/core.js", //
			"/api/requirejs/module_def.js"//
	);
	@Autowired
	PlatformService platformService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			String uri = request.getRequestURI();
			if (uri.startsWith("/api/classpath/query")) {
				return Boolean.TRUE;
			}

			if (excludeUris.contains(uri)) {
				return Boolean.TRUE;
			}
			Cookies cookies = Cookies.from(request);

			if (session(COOKIE_CERES_PLATFORM_ID) != null) {
				cookies.remove(COOKIE_CERES_PLATFORM_KEY);
				cookies.remove(COOKIE_CERES_PLATFORM_SECRET);
				cookies.flushTo(response);
				// 平台已经获得授权，直接通过
				return Boolean.TRUE;
			}

			// 没有认证授权
			// 检测是否拥有key和secret，有则进行授权，没有则报错
			if (!cookies.exist(COOKIE_CERES_PLATFORM_KEY) || !cookies.exist(COOKIE_CERES_PLATFORM_SECRET)) {
				zipOut(Result.error(ErrorCodes.PLATFORM_KEY_AND_SECRET_IS_REQUIRED));
				return Boolean.FALSE;
			} else {
				// 读取key和secret;
				String key = cookies.getValue(COOKIE_CERES_PLATFORM_KEY);
				String secret = cookies.getValue(COOKIE_CERES_PLATFORM_SECRET);
				if (Strings.isNullOrEmpty(key) || Strings.isNullOrEmpty(secret)) {
					zipOut(Result.error(ErrorCodes.PLATFORM_KEY_AND_SECRET_IS_REQUIRED));
					return Boolean.FALSE;
				}
				// 进行校验登录
				Result<Platform> result = platformService.getIndentifyProvider().authentication(key, secret);
				if (!result.isSuccess()) {
					zipOut(result);
					return Boolean.FALSE;
				} else {
					// 生成token
					String token = Random.uuid();
					tokenPool.put(token, result.getObject().getId());
					cookies.remove(COOKIE_CERES_PLATFORM_KEY);
					cookies.remove(COOKIE_CERES_PLATFORM_SECRET);
					cookies.flushTo(response);
					session(COOKIE_CERES_PLATFORM_ID, result.getObject().getId());

					return Boolean.TRUE;
				}
			}

		}
		return Boolean.TRUE;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
