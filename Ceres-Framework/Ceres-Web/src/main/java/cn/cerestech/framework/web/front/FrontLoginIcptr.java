package cn.cerestech.framework.web.front;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.cerestech.framework.core.Core;
import cn.cerestech.framework.persistence.service.MysqlService;
import cn.cerestech.framework.web.Web;

public class FrontLoginIcptr implements HandlerInterceptor {
	public static final String SK_FRONT_USER_ID = "SK_FRONT_USER_ID";
	public static final String SK_FRONT_USER_OBJ = "SK_FRONT_USER_OBJ";

	public static final String COOKIE_FRONT_USER_ID = "ceres_attr_front_user_id";
	public static final String COOKIE_FRONT_USER_REMEMBER = "ceres_attr_front_user_remember";

	@Autowired
	MysqlService mysqlService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception ex) throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView ma) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {

		if (obj instanceof HandlerMethod) {
			HandlerMethod m = (HandlerMethod) obj;
			FrontLoginRequired fl = m.getMethodAnnotation(FrontLoginRequired.class);
			if (fl != null && fl.required()) {

				// 要求前端用户登录
				if (request.getSession(true).getAttribute(SK_FRONT_USER_ID) == null) {
					ResponseBody rb = m.getMethodAnnotation(ResponseBody.class);
					if (rb == null) {
						// 没有注解ResponseBody的就是页面转向
						String loginUrl = Web.loginFrontUrl();
						Device dev = fl.device();
						switch (dev) {
							case WAP:
								loginUrl = Web.loginWapUrl();
							break;
							case WEB:
								loginUrl = Web.loginFrontUrl();
								break;
							default:
								loginUrl = Web.loginFrontUrl();
								break;
						}
						response.sendRedirect(loginUrl + "?forward=" + getRequestUrl(request));
					} else {
						// 注解了ResponseBody的就是要求登录的login返回
						response.getOutputStream().write("login".getBytes(Core.charsetEncoding()));
						response.getOutputStream().flush();
					}
					return Boolean.FALSE;
				}
			}
		}
		return Boolean.TRUE;
	}

	protected String getRequestUrl(HttpServletRequest request) {
		String url = "";
		// url+=""; // 请求服务器
		url += request.getRequestURI(); // 工程名
		if (request.getQueryString() != null) { // 判断请求参数是否为空
			url += "?" + request.getQueryString();
		}// 参数
		return URLEncoder.encode(url);
	}
}
