package cn.cerestech.framework.support.login.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.primitives.Longs;

import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.annotation.LoginRequired;
import cn.cerestech.framework.support.login.entity.LoginEntity;
import cn.cerestech.framework.support.login.enums.ErrorCodes;
import cn.cerestech.framework.support.login.provider.LoginServiceProvider;
import cn.cerestech.framework.support.login.service.LoginService;
import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.framework.support.web.Cookies;
import cn.cerestech.framework.support.web.WebSupport;

@Component
public class LoginInterceptor extends WebSupport implements HandlerInterceptor {

	public static final String COOKIE_CERES_PLATFORM = "ceres_platform";
	public static final String SESSION_LOGINENTITY_ID = "SESSION_LOGINENTITY_ID";

	private Logger log = LogManager.getLogger();

	@Autowired
	private LoginService loginService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod m = (HandlerMethod) handler;
			LoginRequired fl = m.getMethodAnnotation(LoginRequired.class);
			if (fl != null && fl.required()) {

				Cookies cookies = Cookies.from(request);

				// 检查paltform,必须指定platfomr
				if (!cookies.exist(COOKIE_CERES_PLATFORM)) {
					zipOut(Result.error(ErrorCodes.PLATFORM_CATEGORY_REQUIRED));
					return Boolean.FALSE;
				}
				PlatformCategory platform = EnumCollector.forClass(PlatformCategory.class)
						.keyOf(cookies.getValue(COOKIE_CERES_PLATFORM));

				// 检查Session中是否存在登录用户ID
				String sKey = "SESSION_KEY_" + platform.key();

				Long id = (Long) request.getSession(true).getAttribute(sKey);
				if (id == null) {
					// 用户未登录

					// 检测是否有持久化Cookie登录
					String cKey = "COOKIE_REMEMBER_" + platform.key();
					if (!cookies.exist(cKey)) {
						// 如果要求登录、没有登录也没有remember_key，则要求登录
						zipOut(Result.error(ErrorCodes.LOGIN_REQUIRED));
						return Boolean.FALSE;
					} else {
						// 获取remember_key和remember_id
						String remember = cookies.getValue(cKey);
						id = Longs.tryParse(cookies.getValue("COOKIE_REMEMBER_ID_" + platform.key()));
						if (id == null) {
							// id 不存在，数据错误,要求重登录
							zipOut(Result.error(ErrorCodes.LOGIN_REQUIRED));
							return Boolean.FALSE;
						}

						// 校验登录
						LoginServiceProvider serviceProvider = loginService.getServiceProvider(platform);
						if (serviceProvider != null && serviceProvider.validateRememberToken(remember, id)) {
							// 校验通过，记录入Session
							session(sKey, id);
							return Boolean.TRUE;
						} else {
							// 不提供此种类型的持久化登录,要求重新登录
							zipOut(Result.error(ErrorCodes.LOGIN_REQUIRED));
							return Boolean.FALSE;
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

	public void register(PlatformCategory platform, LoginEntity entity, Boolean remember, HttpServletRequest request,
			HttpServletResponse response) {
		// 记录登录信息到cookie中
		IdEntity idEntity = null;
		if (entity instanceof IdEntity) {
			idEntity = (IdEntity) entity;
			session(SESSION_LOGINENTITY_ID, idEntity.getId());// 记录入Session
		}

		String cKeyToken = getCookieKeyRememberToken(platform);
		String cKeyId = getCookieKeyRememberID(platform);
		Cookies c = Cookies.from(request);
		// 如果用户记住登录，还要放入cookie
		if (remember) {
			// 记录登录,添加cookie
			if (idEntity != null) {
				c.add(cKeyId, idEntity.getId().toString());
			}
			c.add(cKeyToken, entity.getRememberToken());
		} else {
			// 清除cookie
			c.remove(cKeyId);
			c.remove(cKeyToken);
		}
		c.flushTo(response);
	}

	public String getCookieKeyRememberToken(PlatformCategory platform) {
		return "COOKIE_REMEMBER_TOKEN_" + platform.key();
	}

	public String getCookieKeyRememberID(PlatformCategory platform) {
		return "COOKIE_REMEMBER_ID_" + platform.key();
	}

}
