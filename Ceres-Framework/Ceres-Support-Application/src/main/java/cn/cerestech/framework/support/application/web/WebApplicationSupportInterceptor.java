package cn.cerestech.framework.support.application.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.json.Jsonable;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.application.enums.ApplicationKey;
import cn.cerestech.framework.support.application.errorcodes.ApplicationErrorCodes;
import cn.cerestech.framework.support.application.service.ApplicationService;

@Service
public class WebApplicationSupportInterceptor implements HandlerInterceptor {

	@Autowired
	ApplicationService applicationService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String acceess_token = request.getParameter(ApplicationKey.SUPPORT_APPLICATION_ACCESS_TOKEN.key());
		if (Strings.isNullOrEmpty(acceess_token)) {
			// 如果token为空，检查是否登录请求
			String appId = request.getParameter(ApplicationKey.SUPPORT_APPLICATION_APPID.key());
			String appSecret = request.getParameter(ApplicationKey.SUPPORT_APPLICATION_APPSECRET.key());
			if (!Strings.isNullOrEmpty(appId) && !Strings.isNullOrEmpty(appSecret)) {
				// 需要认证
				Result<String> authResult = applicationService.authenticateAppIdSecrect(appId, appSecret);
				output(response, authResult);
				return Boolean.FALSE;
			} else {
				// 都没有，显示token过期
				output(response, Result.error(ApplicationErrorCodes.ACCESS_TOKEN_EXPIRED));
				return Boolean.FALSE;
			}
		}

		// 验证token
		Result<String> valiResult = applicationService.validateToken(acceess_token);
		if (!valiResult.isSuccess()) {
			// 验证失败
			output(response, valiResult);
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}

	}

	private void output(HttpServletResponse response, Jsonable jsonable) throws Exception {
		response.addHeader("Content-Type", "application/json");
		response.getWriter().write(jsonable.toPretty());
	}

}
