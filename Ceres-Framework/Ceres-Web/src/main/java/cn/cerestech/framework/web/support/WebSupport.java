package cn.cerestech.framework.web.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.LiteDeviceResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.Core;
import cn.cerestech.framework.core.HttpIOutils;
import cn.cerestech.framework.core.IPSeeker;
import cn.cerestech.framework.core.Logable;
import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.core.json.Jsonable;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.persistence.service.MysqlService;
import cn.cerestech.framework.web.WebUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class WebSupport implements Logable {

	protected static final String CONTENT_ENCODING = "gzip";
	protected static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("HH:mm:ss");
	protected static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
	protected static final SimpleDateFormat FORMAT_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected static final SimpleDateFormat FORMAT_MILLISECOND = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * 浏览器标志
	 * 
	 * @author <a href="mailto:royrxc@gmail.com">Roy</a>
	 * @since 2016年1月12日下午3:34:41
	 */
	public static enum BrowserSimbol implements DescribableEnum {
		MP("micromessenger", "微信浏览器");
		private String key, desc;

		private BrowserSimbol(String key, String desc) {
			this.key = key;
			this.desc = desc;
		}

		@Override
		public String key() {
			return this.key;
		}

		@Override
		public String desc() {
			return this.desc;
		}
	}

	protected IPSeeker ipseeker = new IPSeeker();

	@Autowired
	protected MysqlService mysqlService;

	@Autowired
	private FreeMarkerConfigurer freemarkerConfig;

	protected HttpServletRequest getRequest() {
		return WebUtils.getCurrentRequest();
	}

	protected HttpServletResponse getResponse() {
		return WebUtils.getCurrentResponse();
	}

	protected void redirect(String url, String params) {
		try {
			getResponse().sendRedirect(url + (StringUtils.isBlank(params) ? "" : ("?" + params)));
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
	 * 输出流，向response 输出数据
	 * 
	 * @param content
	 */
	protected void out(String content) {
		HttpIOutils.outputContent(getResponse(), content, "UTF-8");
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
		zipOut(jsonObj.toJson());
	}

	protected void zipOut(List<?> list) {
		zipOut(Jsons.toJson(list));
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
			response.setHeader("Content-Encoding", CONTENT_ENCODING);
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
		if (StringUtils.isBlank(key)) {
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

	/**
	 * 加载freemarker的模板
	 * 
	 * @param key
	 * @return
	 */
	protected Template getTemplate(String key) {
		try {
			return freemarkerConfig.getConfiguration().getTemplate(key);
		} catch (IOException e) {
			log.throwing(e);
		}
		return null;
	}

	protected String freemarker(String template, Map<String, Object> param) {
		Configuration cfg = freemarkerConfig.getConfiguration();
		TemplateLoader oldLoader = cfg.getTemplateLoader();

		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate("instantTempalte", template);
		cfg.setTemplateLoader(stringLoader);

		StringWriter writer = new StringWriter();

		try {
			Template templateObj = cfg.getTemplate("instantTempalte");
			templateObj.process(param, writer);
		} catch (TemplateException | IOException e) {
			log.catching(e);
			return "";
		} finally {
			cfg.setTemplateLoader(oldLoader);
		}
		return writer.toString();
	}

	protected boolean isMobile() {
		return new LiteDeviceResolver().resolveDevice(getRequest()).isMobile();
	}

	protected boolean deviceFor(String matcher) {
		String userAgent = Strings.nullToEmpty(getRequest().getHeader("User-Agent")).toLowerCase();
		if (userAgent.indexOf(matcher) > 0) {// 是微信浏览器
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public String getIpAddr() {
		HttpServletRequest request = getRequest();
		if (request == null) {
			return "";
		}
		String ip = request.getHeader("X-Forwarded-For");// 真实的ip 非代理
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");// 代理ip
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		return ip == null ? "127.0.0.1" : ip;
	}

	public void putRequestAttr(String name, Object o) {
		getRequest().setAttribute(name, o);
	}
}
