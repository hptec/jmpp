package cn.cerestech.framework.support.starter.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.starter.annotation.PlatformRequired;
import cn.cerestech.framework.support.starter.dao.PlatformDao;
import cn.cerestech.framework.support.starter.entity.Platform;
import cn.cerestech.framework.support.starter.enums.ErrorCodes;
import cn.cerestech.framework.support.starter.operator.PlatformOperator;
import cn.cerestech.framework.support.starter.operator.SessionOperator;
import cn.cerestech.framework.support.starter.operator.ZipOutOperator;

@Component
public class PlatformInterceptor implements HandlerInterceptor, ZipOutOperator, SessionOperator, PlatformOperator {

	@Autowired
	PlatformDao platformDao;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (handler instanceof HandlerMethod) {
			HandlerMethod m = (HandlerMethod) handler;
			PlatformRequired fl = m.getMethodAnnotation(PlatformRequired.class);
			if (fl != null && fl.required()) {

				// Session中是否有存放
				Long pid = getPlatformId();
				if (pid != null) {
					// 会话中存在，并且设置为预加载数据
					Platform platform = platformDao.findOne(pid);
					putRequest(PLATFORM_OBJECT, platform);
				} else {
					// Session中不存在，进行初始化
					String key = getPlatformKey();
					if (Strings.isNullOrEmpty(key)) {
						// 前段没有提交Key
						zipOut(Result.error(ErrorCodes.PLATFORM_CATEGORY_REQUIRED));
						return Boolean.FALSE;
					} else {
						Platform platform = platformDao.findUniqueByKey(key);
						if (platform == null) {
							// 数据库中没有Key对应的记录
							zipOut(Result.error(ErrorCodes.PLATFORM_CATEGORY_REQUIRED));
							return Boolean.FALSE;
						} else {
							// 将记录放入相应位置
							putSession(PLATFORM_ID, platform.getId());// Session中放Id
							putRequest(PLATFORM_OBJECT, platform);// Request中放对象
						}
					}
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
