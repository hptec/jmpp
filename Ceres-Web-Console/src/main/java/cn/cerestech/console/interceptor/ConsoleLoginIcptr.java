package cn.cerestech.console.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;
import com.google.common.primitives.Longs;

import cn.cerestech.console.annotation.LoginRequired;
import cn.cerestech.console.entity.Employee;
import cn.cerestech.framework.core.Core;
import cn.cerestech.framework.persistence.service.MysqlService;
import cn.cerestech.framework.support.web.Cookies;

public class ConsoleLoginIcptr implements HandlerInterceptor {
	public static final String SK_CONSOLE_USER_ID = "SK_USER_ID";
	public static final String SK_CONSOLE_USER_OBJ = "SK_USER_OBJ";

	public static final String COOKIE_CONSOLE_USER_ID = "ceres_attr_console_user_id";
	public static final String COOKIE_CONSOLE_USER_REMEMBER = "ceres_attr_console_user_remember";

	public static final String LOGIN_URL = "/$$ceres_sys/console/base/res/cp?id=console/res/templates/employee/login.html";

	@Autowired
	MysqlService mysqlService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception ex)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView ma)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		if (obj instanceof HandlerMethod) {
			HandlerMethod m = (HandlerMethod) obj;
			LoginRequired fl = m.getMethodAnnotation(LoginRequired.class);
			if (fl != null && fl.required()) {

				// 要求前端用户登录
				if (request.getSession(true).getAttribute(SK_CONSOLE_USER_ID) == null) {
					// 如果用户没有登录会话，尝试恢复
					Cookies c = Cookies.on(request, response);
					String id = c.getValue(COOKIE_CONSOLE_USER_ID);
					String rememberStr = c.getValue(COOKIE_CONSOLE_USER_REMEMBER);
					if (!Strings.isNullOrEmpty(id)) {
						Employee e = mysqlService.queryById(Employee.class, Longs.tryParse(id));
						if (e != null) {
							// 用户存在
							if (e.getRemember_expired() != null) {
								if (e.getRemember_expired().after(new Date())) {
									// 登录状态没有过期
									if (Strings.nullToEmpty(e.getRemember_token()).equals(rememberStr)) {
										// 记录登录状态
										request.getSession().setAttribute(SK_CONSOLE_USER_ID, e.getId());
										request.getSession().setAttribute(SK_CONSOLE_USER_OBJ, e);
										return Boolean.TRUE;
									}
								}
							}
							// 强制删除Cookie
							c.remove(COOKIE_CONSOLE_USER_ID).remove(COOKIE_CONSOLE_USER_REMEMBER).flush();
						}
					}

					switch (fl.redirectType()) {
					case UNKNOWN:
						// 未指定转向方式 || 转向方式未知
						ResponseBody rb = m.getMethodAnnotation(ResponseBody.class);
						if (rb == null) {
							// 没有注解ResponseBody的就是页面转向
							String ctx = request.getContextPath();
							response.sendRedirect(ctx + LOGIN_URL);
						} else {
							// 注解了ResponseBody的就是要求登录的login返回
							response.getOutputStream().write("login".getBytes(Core.charsetEncoding()));
							response.getOutputStream().flush();
							response.getOutputStream().close();
						}
						break;
					case URL:
						// 指定转向方式为URL;
						String ctx = request.getContextPath();
						response.sendRedirect(ctx + LOGIN_URL);
						break;
					default:
						response.getOutputStream().write("login".getBytes(Core.charsetEncoding()));
						response.getOutputStream().flush();
						response.getOutputStream().close();
						// 默认为JSON转向
					}

					return Boolean.FALSE;
				}
			}
		}
		return Boolean.TRUE;
	}
}
