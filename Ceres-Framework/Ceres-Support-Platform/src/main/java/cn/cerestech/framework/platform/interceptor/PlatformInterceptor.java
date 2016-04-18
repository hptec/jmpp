package cn.cerestech.framework.platform.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.platform.annotation.PlatformIgnore;
import cn.cerestech.framework.platform.entity.Platform;
import cn.cerestech.framework.platform.enums.ErrorCodes;
import cn.cerestech.framework.platform.service.PlatformService;
import cn.cerestech.framework.support.web.Cookies;
import cn.cerestech.framework.support.web.WebSupport;

@Component
public class PlatformInterceptor extends WebSupport implements HandlerInterceptor {

	public static final String COOKIE_CERES_PLATFORM_AUTHCODE = "ceres_platform_authcode";

	// private Logger log = LogManager.getLogger();

	private Set<String> excludeUris = Sets.newHashSet(//
			"/api/web/systemconfigs.js"//
	);
	@Autowired
	PlatformService platformService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (handler instanceof HandlerMethod) {
			HandlerMethod m = (HandlerMethod) handler;

			if (m.getMethodAnnotation(PlatformIgnore.class) != null) {
				// 忽略保护
				return Boolean.TRUE;
			}

			String uri = request.getRequestURI();
			if (uri.startsWith("/api/classpath/query")) {
				return Boolean.TRUE;
			}

			if (excludeUris.contains(uri)) {
				return Boolean.TRUE;
			}
			Cookies cookies = Cookies.from(request);

			if (session(COOKIE_CERES_PLATFORM_ID) != null) {
				cookies.remove(COOKIE_CERES_PLATFORM_AUTHCODE);
				cookies.flushTo(response);
				// 平台已经获得授权，直接通过
				return Boolean.TRUE;
			}

			// 没有认证授权
			// 检测是否拥有authcode，有则进行授权，没有则报错

			if (request.getParameter(COOKIE_CERES_PLATFORM_AUTHCODE) == null) {
				zipOut(Result.error(ErrorCodes.PLATFORM_KEY_AND_SECRET_IS_REQUIRED));
				return Boolean.FALSE;
			} else {
				// 读取authcode
				String authcode = cookies.getValue(COOKIE_CERES_PLATFORM_AUTHCODE);
				if (Strings.isNullOrEmpty(authcode)) {
					zipOut(Result.error(ErrorCodes.PLATFORM_KEY_AND_SECRET_IS_REQUIRED));
					return Boolean.FALSE;
				}
				// 进行校验登录
				Platform platform = platformService.validate(authcode);
				if (platform == null) {
					zipOut(Result.error(ErrorCodes.PLATFORM_KEY_AND_SECRET_IS_REQUIRED));
					return Boolean.FALSE;
				} else {
					// 生成token
					cookies.remove(COOKIE_CERES_PLATFORM_AUTHCODE);
					cookies.flushTo(response);
					session(COOKIE_CERES_PLATFORM_ID, platform.getId());

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
