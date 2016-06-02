package cn.cerestech.framework.support.login.interceptor;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.beust.jcommander.internal.Maps;
import com.google.common.base.Strings;

import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.annotation.LoginRequired;
import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.login.entity.Loginable;
import cn.cerestech.framework.support.login.enums.ErrorCodes;
import cn.cerestech.framework.support.login.operator.UserSessionOperator;
import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.framework.support.web.operator.PlatformOperator;
import cn.cerestech.framework.support.web.operator.SessionOperator;
import cn.cerestech.framework.support.web.operator.ZipOutOperator;

@Component
public class LoginInterceptor
		implements HandlerInterceptor, ZipOutOperator, SessionOperator, PlatformOperator, UserSessionOperator {

	@SuppressWarnings("rawtypes")
	private static final Map<PlatformCategory, LoginDao> loginProviderPool = Maps.newHashMap();

	public static final String COOKIE_CERES_PLATFORM = "ceres_platform";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 开发模式专用
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept"); 

		if (handler instanceof HandlerMethod) {
			HandlerMethod m = (HandlerMethod) handler;
			LoginRequired fl = m.getMethodAnnotation(LoginRequired.class);
			if (fl != null && fl.required()) {

				// 检查paltform,必须指定platfomr
				PlatformCategory platform = getPlatformCategory();
				if (platform == null) {
					zipOut(Result.error(cn.cerestech.framework.platform.enums.ErrorCodes.PLATFORM_CATEGORY_REQUIRED));
					return Boolean.FALSE;
				}

				// 检查Session中是否存在登录用户ID

				Long id = getUserId();
				if (id == null) {
					// 用户未登录

					// 检测是否有持久化Cookie登录
					String token = getRememberToken();
					if (Strings.isNullOrEmpty(token)) {
						// 如果要求登录、没有登录也没有token，则要求登录
						zipOut(Result.error(ErrorCodes.LOGIN_REQUIRED));
						return Boolean.FALSE;
					}
					// 获取remember_key和remember_id
					id = getRememberId();
					if (id == null) {
						// id 不存在，数据错误,要求重登录
						zipOut(Result.error(ErrorCodes.LOGIN_REQUIRED));
						return Boolean.FALSE;
					}

					// 校验登录
					LoginDao dao = getDao();
					if (dao != null && dao.findUniqueByIdAndLoginRememberTokenAndLoginRememberExpiredGreaterThan(id,
							token, new Date()) != null) {
						// 校验通过，记录入Session
						putUserId(id);
						return Boolean.TRUE;
					} else {
						// 不提供此种类型的持久化登录,要求重新登录
						zipOut(Result.error(ErrorCodes.LOGIN_REQUIRED));
						return Boolean.FALSE;
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

	public void register(Loginable login, Boolean remember) {
		Long id = login instanceof IdEntity ? ((IdEntity) login).getId() : null;

		// 记录登录信息到cookie中
		putUserId(id);

		// 如果用户记住登录，还要放入cookie
		if (remember) {
			// 记录登录,添加cookie
			putRemember(id, login.getLogin().getRememberToken());
		} else {
			// 清除cookie
			clearRemember();
		}
	}

	/**
	 * 存储LoginProvider，不重复存储
	 * 
	 * @param category
	 * @param provider
	 */
	public void putDao(LoginDao provider) {
		PlatformCategory category = getPlatformCategory();
		if (!loginProviderPool.containsKey(category)) {
			loginProviderPool.put(category, provider);
		}
	}

	public LoginDao getDao() {
		return loginProviderPool.get(getPlatformCategory());
	}

}
