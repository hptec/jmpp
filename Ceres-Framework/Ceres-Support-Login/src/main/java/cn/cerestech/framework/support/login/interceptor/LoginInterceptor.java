package cn.cerestech.framework.support.login.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.annotation.LoginRequired;
import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.login.enums.LoginErrorCodes;
import cn.cerestech.framework.support.login.operator.UserSessionOperator;
import cn.cerestech.framework.support.login.provider.LoginProvider;
import cn.cerestech.framework.support.login.service.LoginService;
import cn.cerestech.framework.support.persistence.entity.SoftDelete;
import cn.cerestech.framework.support.starter.operator.SessionOperator;
import cn.cerestech.framework.support.starter.operator.ZipOutOperator;

@Component
public class LoginInterceptor implements HandlerInterceptor, ZipOutOperator, SessionOperator, UserSessionOperator {

	public static final String COOKIE_CERES_PLATFORM = "ceres_platform";

	@SuppressWarnings("rawtypes")
	@Autowired
	LoginProvider loginProvider;

	@SuppressWarnings("rawtypes")
	@Autowired
	LoginDao loginDao;

	@SuppressWarnings("rawtypes")
	@Autowired
	LoginService loginService;

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

				// 检查Session中是否存在登录用户ID

				Long id = getUserId();
				if (id == null) {
					// 用户未登录

					// 检测是否有持久化Cookie登录
					String token = loginService.getRememberToken();
					if (Strings.isNullOrEmpty(token)) {
						// 如果要求登录、没有登录也没有token，则要求登录
						zipOut(Result.error(LoginErrorCodes.LOGIN_REQUIRED));
						return Boolean.FALSE;
					}
					// 获取remember_key和remember_id
					id = loginService.getRememberId();
					if (id == null) {
						// id 不存在，数据错误,要求重登录
						zipOut(Result.error(LoginErrorCodes.LOGIN_REQUIRED));
						return Boolean.FALSE;
					}

					// 校验登录
					if (loginDao != null
							&& loginDao.findUniqueByIdAndLoginRememberTokenAndLoginRememberExpiredGreaterThan(id, token,
									new Date()) != null) {
						// 判断用户是否有可能被删除
						Object obj = loginDao.findOne(id);
						if (obj instanceof SoftDelete) {
							if (((SoftDelete) obj).getDeleteTime() != null) {
								// 已经被删除了
								zipOut(Result.error(LoginErrorCodes.LOGIN_REQUIRED));
								return Boolean.FALSE;
							}
						}

						// 校验通过，记录入Session
						putUserId(id);
						return Boolean.TRUE;
					} else {
						// 不提供此种类型的持久化登录,要求重新登录
						zipOut(Result.error(LoginErrorCodes.LOGIN_REQUIRED));
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

}
