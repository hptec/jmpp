package cn.cerestech.framework.support.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.Core;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.core.json.Jsonable;
import cn.cerestech.framework.core.json.Jsons;

public abstract class WebSupport {

	public static final String COOKIE_CERES_PLATFORM = "ceres_platform";
	public static final String COOKIE_CERES_PLATFORM_ID = "COOKIE_CERES_PLATFORM_ID";

	private Logger log = LogManager.getLogger();

	protected HttpServletRequest getRequest() {
		return WebUtils.getCurrentRequest();
	}

	protected HttpServletResponse getResponse() {
		return WebUtils.getCurrentResponse();
	}

	protected void redirect(String url, String params) {
		try {
			getResponse().sendRedirect(url + (Strings.isNullOrEmpty(params) ? "" : ("?" + params)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void redirect(String url) {
		redirect(url, null);
	}

	protected void forward(String url) {
		try {
			getRequest().getRequestDispatcher(url).forward(getRequest(), getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * gzip 方式输出数据
	 * 
	 * @param content
	 */
	protected void zipOut(String content) {
		zipOut(content, "text/html");
	}

	protected void zipOut(Jsonable jsonObj) {
		zipOut(jsonObj.getJson().toJson(), "application/json");
	}

	protected void zipOut(Jsons jsonObj) {
		zipOut(jsonObj.toJson(), "application/json");
	}

	protected void zipOut(List<?> list) {
		zipOut(Jsons.from(list).toJson());
	}

	/**
	 * gzip 方式输出数据
	 * 
	 * @param content
	 */
	protected void zipOut(String content, String contentType) {
		byte[] bytes = new byte[0];
		try {
			getResponse().setCharacterEncoding(Core.charsetEncoding());
			bytes = content.getBytes(Core.charsetEncoding());
			zipOut(bytes, contentType);
		} catch (UnsupportedEncodingException e) {
			log.catching(e);
		}
	}

	/**
	 * gzip 方式输出数据
	 * 
	 * @param bytes
	 */
	protected void zipOut(byte[] bytes, String contentType) {
		HttpServletResponse response = this.getResponse();
		try {
			response.addHeader("Content-Encoding", "gzip");
			response.setCharacterEncoding(Core.charsetEncoding());
			response.setContentType(contentType);
			response.setLocale(Locale.CHINA);
			ByteArrayOutputStream bais = new ByteArrayOutputStream();
			// 压缩
			GZIPOutputStream gos = new GZIPOutputStream(bais, true);
			gos.write(bytes, 0, bytes.length);
			gos.finish();
			byte[] res = bais.toByteArray();
			// 关闭输出流
			bais.flush();
			bais.close();
			ServletOutputStream out = response.getOutputStream();
			out.write(res);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否是刚创建的session
	 * 
	 * @return
	 */
	protected boolean isNewSession() {
		return session().isNew();
	}

	/**
	 * 获取当前session
	 * 
	 * @return
	 */
	protected HttpSession session() {
		return getRequest().getSession();
	}

	/**
	 * 添加session 缓存数据
	 * 
	 * @param key
	 * @param obj
	 */
	protected void session(String key, Object obj) {
		getRequest().getSession(true).setAttribute(key, obj);
	}

	/**
	 * 获取session 缓存数据
	 * 
	 * @param key
	 * @return
	 */
	protected Object session(String key) {
		return getRequest().getSession(true).getAttribute(key);
	}

	/**
	 * 移除session 中的变量
	 * 
	 * @param key
	 */
	protected void removeSession(String key) {
		try {
			getRequest().getSession(true).removeAttribute(key);
		} catch (Exception e) {
		}
	}

	protected String requestParam(String key) {
		return getRequest().getParameter(key);
	}

	protected String[] requestParamArr(String key) {
		return getRequest().getParameterValues(key);
	}

	protected void addCookie(String key, String value) {
		try {
			Cookie c = new Cookie(key, URLEncoder.encode(value, Core.charsetEncoding()));
			c.setPath("/");
			getResponse().addCookie(c);
		} catch (UnsupportedEncodingException e) {
			System.err.println("URLEncoder.encode cookie value error with value:" + value);
		}
	}

	protected String getCookie(String key) {
		String val = null;
		if (Strings.isNullOrEmpty(key)) {
			return val;
		}
		Cookie[] datas = getRequest().getCookies();
		if (datas == null || datas.length == 0) {
			return val;
		}

		List<Cookie> res = Arrays.asList(datas).stream().filter(t -> {
			if (key.equals(t.getName())) {
				return true;
			} else {
				return false;
			}
		}).collect(Collectors.toList());

		if (res != null && !res.isEmpty()) {
			val = res.get(0).getValue();
		}

		String tmp = null;
		try {
			tmp = URLDecoder.decode(Strings.nullToEmpty(val), Core.charsetEncoding());
		} catch (Exception e) {
			tmp = val;
		}
		return tmp;
	}

	protected Map<String, String> parseParameter() {
		Enumeration<String> keys = getRequest().getParameterNames();
		Map<String, String> map = Maps.newHashMap();

		while (keys.hasMoreElements()) {
			String k = keys.nextElement();
			map.put(k, getRequest().getParameter(k));
		}
		return map;
	}

	public void putRequestAttr(String name, Object o) {
		getRequest().setAttribute(name, o);
	}

	protected PlatformCategory getPlatformCategory() {
		Cookies cookies = Cookies.from(getRequest());
		String strPlatform = cookies.getValue(COOKIE_CERES_PLATFORM);
		if (Strings.isNullOrEmpty(strPlatform)) {
			return null;
		} else {
			return EnumCollector.forClass(PlatformCategory.class).keyOf(strPlatform);
		}
	}

	protected Long getPlatformId() {
		return (Long) session(COOKIE_CERES_PLATFORM_ID);
	}

	protected void zipOutRequireJson(Object obj) {
		StringBuffer buffer = new StringBuffer("define(function() {");
		buffer.append("return ");
		buffer.append(Jsons.from(obj).toJson());
		buffer.append("});");
		if (log.isTraceEnabled()) {
			log.trace(Jsons.from(obj).prettyPrint().toJson());
		}
		zipOut(buffer.toString(), "application/javascript");
	}
}
