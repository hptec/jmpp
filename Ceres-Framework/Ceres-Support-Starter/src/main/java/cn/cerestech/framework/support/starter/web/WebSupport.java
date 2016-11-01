package cn.cerestech.framework.support.starter.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.google.common.base.Strings;

import cn.cerestech.framework.support.starter.operator.RequestOperator;
import cn.cerestech.framework.support.starter.operator.ResponseOperator;
import cn.cerestech.framework.support.starter.operator.SessionOperator;
import cn.cerestech.framework.support.starter.operator.ZipOutOperator;
import freemarker.template.Template;

public abstract class WebSupport implements RequestOperator, ResponseOperator, ZipOutOperator, SessionOperator {
	protected static final String PAGE_ENCODING = "UTF-8";
	protected Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	private FreeMarkerConfigurer freemarkerConfig;
	
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
	
	protected void addCookie(String key, String value) {
		try {
			Cookie c=new Cookie(key, URLEncoder.encode(value, PAGE_ENCODING));
			c.setPath("/");
			getResponse().addCookie(c);
		} catch (UnsupportedEncodingException e) {
			System.err.println("URLEncoder.encode cookie value error with value:"+value);
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
			tmp = URLDecoder.decode(Strings.nullToEmpty(val), PAGE_ENCODING);
		} catch (Exception e) {
			tmp = val;
		}
		return tmp;
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

}
