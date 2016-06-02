package cn.cerestech.framework.platform.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.platform.annotation.PlatformCategoryRequired;
import cn.cerestech.framework.platform.enums.ErrorCodes;
import cn.cerestech.framework.support.web.operator.PlatformOperator;
import cn.cerestech.framework.support.web.operator.SessionOperator;
import cn.cerestech.framework.support.web.operator.ZipOutOperator;

@Component
public class PlatformInterceptor implements HandlerInterceptor, ZipOutOperator, SessionOperator, PlatformOperator {

	public static final String COOKIE_CERES_PLATFORM = "ceres_platform";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (handler instanceof HandlerMethod) {
			HandlerMethod m = (HandlerMethod) handler;
			PlatformCategoryRequired fl = m.getMethodAnnotation(PlatformCategoryRequired.class);
			if (fl != null && fl.required()) {

				// 检查paltform,必须指定platfomr
				PlatformCategory platform = getPlatformCategory();
				if (platform == null) {
					zipOut(Result.error(ErrorCodes.PLATFORM_CATEGORY_REQUIRED));
					return Boolean.FALSE;
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
