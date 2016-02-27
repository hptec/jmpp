package cn.cerestech.middleware.weixin.mp.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.Core;
import cn.cerestech.framework.core.DateUtil;
import cn.cerestech.framework.core.Logable;
import cn.cerestech.middleware.weixin.entity.MpUser;
import cn.cerestech.middleware.weixin.entity.Status;
import cn.cerestech.middleware.weixin.mp.annotation.MpUserRequired;
import cn.cerestech.middleware.weixin.mp.annotation.MpUserRequired.Level;
import cn.cerestech.middleware.weixin.mp.api.MpApi;
import cn.cerestech.middleware.weixin.mp.entity.UserToken;
import cn.cerestech.middleware.weixin.mp.enums.AuthorizeScope;
import cn.cerestech.middleware.weixin.mp.service.WeiXinMpService;
import cn.cerestech.middleware.weixin.service.MpUserService;
import cn.cerestech.middleware.weixin.service.WeiXinService;

public class MpUserRequireInteceptor implements HandlerInterceptor, Logable {

	@Autowired
	MpUserService mpuserService;

	@Autowired
	WeiXinMpService weixinMpService;

	@Autowired
	WeiXinService weixinService;

	public static final String OPENID = "cur_session_openId";
	public static final String OPENID_COOKIE = "cur_user_oid";
	public static final String ACCESSTOKEN = "cur_session_access_token";
	public static final String REFRESH_TOKEN = "refresh_token";

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception ex)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView ma)
			throws Exception {

	}

	private void setValue(HttpServletRequest request, HttpServletResponse response, String openId, String accessToke,
			String refreshToken) {
		setSession(request, OPENID, openId);
		addCookie(response, OPENID_COOKIE, openId);
		setSession(request, ACCESSTOKEN, accessToke);
		setSession(request, REFRESH_TOKEN, refreshToken);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		if (obj instanceof HandlerMethod) {
			HandlerMethod m = (HandlerMethod) obj;
			MpUserRequired mpr = m.getMethodAnnotation(MpUserRequired.class);

			if (mpr != null && (mpr.required().equals(Level.NORMAL)
					|| (mpr.required().equals(Level.BROWSER_SUPPORT) && isMpbrowser(request)))) {

				if (mpr.force()) {
					setValue(request, response, null, null, null);
				}

				String openId = (String) getSession(request, OPENID);
				if (Strings.isNullOrEmpty(openId)) {
					if(mpr.forJs()){//js 请求的数据，返回字符串“flush_page”
						response.getOutputStream().write("flush_page".getBytes(Core.charsetEncoding()));
						response.getOutputStream().flush();
						return false;
					}
					
					// openId为空，开始刷新
					String code = request.getParameter("code");
					if (Strings.isNullOrEmpty(code)) {
						// code 不存在，说明不是从微信连接来的，从微信进行转向
						requireBaseAuthorize(response, request.getRequestURI(),
								mpr.type().equals(AuthorizeScope.SNSAPI_BASE));
						return false;
					} else { // code存在，开始刷新
						// 通过code 换取用户的基本信息
						MpApi api = MpApi.on(weixinService.getProfile());

						Status<UserToken> s = api.USER.grantToken(code);
						request.removeAttribute("code");
						if (s.isSuccess()) {
							String access_token = s.getObject().getAccess_token();
							String oid = s.getObject().getOpenid();
							String rt = s.getObject().getRefresh_token();
							log.trace("授权AccessToken获取成功! [ access_token=" + access_token + ", oid=" + oid + ", rt="
									+ rt + " ]");

							// 双检查检查openid是否放入
							openId = (String) getSession(request, OPENID);
							if (Strings.isNullOrEmpty(openId)) {
								// openId没有放入，说明用户数据肯定没有同步，进行同步
								setValue(request, response, oid, access_token, rt);

								// 检查用户是否写入数据库//只有snsapi_userinfo方式才能使用这种方式同步
								if (!Strings.isNullOrEmpty(oid)) {
									MpUser old = mpuserService.checkForUpdate(oid);

									if (old != null && (old.getUpdate_time() == null
											|| old.getUpdate_time().before(DateUtil.addDate(new Date(), -1)))) {
										// 如果没有更新过，或者更新时间大于一天才进行更新

										if (mpr.type().equals(AuthorizeScope.SNSAPI_USERINFO)) {
											// update 微信用户的基本信息
											MpUser mpuser = api.USER.snsapiUserInfo(s.getObject(), oid).getObject();
											if (mpuser != null) {
												mpuserService.checkForUpdate(mpuser);
											}
										} else {
											// 通过后台的方式更新
											log.trace("Base 方式: 通过后台刷新Openid: " + oid);
											synchMpuser(oid);
										}

									}
								}
							} else {
								// openid如果放入，说明之前已经有请求执行过了，忽略
							}
						}else{
							requireBaseAuthorize(response, request.getRequestURI(),
									mpr.type().equals(AuthorizeScope.SNSAPI_BASE));
							return false;
						}
					}

				} else {// open id 存在
				}
			}

		}
		return Boolean.TRUE;

	}

	private boolean isMpbrowser(HttpServletRequest request) {
		String mark = "micromessenger";
		String userAgent = Strings.nullToEmpty(request.getHeader("User-Agent")).toLowerCase();
		if (userAgent.indexOf(mark) > 0) {// 是微信浏览器
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public void requireBaseAuthorize(HttpServletResponse response, String uri, boolean base) {

		MpApi api = MpApi.on(weixinService.getProfile());
		String url = api.USER.authroizeBase(uri);
		log.trace("WeiXin Redirect to: " + url);
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			log.catching(e);
		}
	}

	public synchronized void synchMpuser(String openid) {
		weixinMpService.synchronizedMpuser(openid);
	}

	/**
	 * 添加session 缓存数据
	 * 
	 * @param key
	 * @param obj
	 */
	protected void setSession(HttpServletRequest request, String key, Object obj) {
		request.getSession(true).setAttribute(key, obj);
	}

	/**
	 * 获取session 缓存数据
	 * 
	 * @param key
	 * @return
	 */
	protected Object getSession(HttpServletRequest request, String key) {
		return request.getSession(true).getAttribute(key);
	}

	protected void addCookie(HttpServletResponse response, String key, String value) {
		try {
			Cookie c = new Cookie(key, URLEncoder.encode(Strings.nullToEmpty(value), Core.charsetEncoding()));
			c.setPath("/");
			response.addCookie(c);
		} catch (UnsupportedEncodingException e) {
			System.err.println("URLEncoder.encode cookie value error with value:" + value);
		}
	}
}
