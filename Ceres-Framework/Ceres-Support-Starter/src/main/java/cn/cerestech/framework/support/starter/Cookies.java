package cn.cerestech.framework.support.starter;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

public class Cookies {

	private Map<String, Cookie> cookies = Maps.newHashMap();

	private String defaultDomain = null;
	private String defaultPath = "/";
	private Integer defaultMaxAgeSec = Integer.MAX_VALUE;
	private Boolean defaultSecure = Boolean.FALSE;

	private Cookies() {
	}

	public static Cookies from(HttpServletRequest request) {
		Cookies cks = new Cookies();
		Cookie[] cs = request.getCookies();
		if (cs != null) {
			for (Cookie c : cs) {
				cks.cookies.put(c.getName(), c);
			}
		}
		return cks;
	}

	public static Cookies from(String defaultDomain, String defaultPath, Integer defaultMaxAgeSec,
			Boolean defaultSecure) {
		Cookies cks = new Cookies();
		cks.defaultDomain = defaultDomain;
		cks.defaultPath = defaultPath;
		cks.defaultMaxAgeSec = defaultMaxAgeSec;
		cks.defaultSecure = cks.defaultSecure;
		return cks;
	}

	public static Cookies from() {
		return new Cookies();
	}

	public Cookies add(String name, String value, String path, String domain, Integer maxAge, Boolean secure,
			String comments) {
		Cookie c = new Cookie(name, value);

		c.setPath(Strings.isNullOrEmpty(path) ? defaultPath : path);
		if (!Strings.isNullOrEmpty(domain)) {
			c.setDomain(domain);
		} else if (!Strings.isNullOrEmpty(defaultDomain)) {
			c.setDomain(defaultDomain);
		}

		c.setMaxAge(maxAge == null ? defaultMaxAgeSec : maxAge);// 设置cookie经过多长秒后被删除。如果0，就说明立即删除。如果是负数就表明当浏览器关闭时自动删除。
		c.setSecure(secure == null ? defaultSecure : secure);
		c.setComment(comments);
		cookies.put(name, c);
		return this;
	}

	public Cookies add(String name, String value, String path, Integer maxAge, String comments) {
		add(name, value, path, null, maxAge, null, comments);
		return this;
	}

	public Cookies add(String name, String value, Integer maxAge) {
		add(name, value, null, null, maxAge, null, null);
		return this;
	}

	public Cookies add(String name, String value) {
		add(name, value, null, null, null, null, null);
		return this;
	}

	public Cookies add(Cookie c) {
		cookies.put(c.getName(), c);
		return this;
	}

	public String getValue(String cookieName) {
		if (!Strings.isNullOrEmpty(cookieName)) {
			Cookie cookie = getCookie(cookieName);
			if (cookie != null) {
				return cookie.getValue();
			}
		}
		return null;
	}

	public Cookie getCookie(String cookieName) {
		return cookies.get(cookieName);
	}

	public Cookies remove(String cookieName) {
		if (!Strings.isNullOrEmpty(cookieName)) {
			Cookie cookie = getCookie(cookieName);
			if (cookie != null) {
				cookie.setMaxAge(0);// 如果0，就说明立即删除
				cookie.setPath("/");// 不要漏掉
				cookies.put(cookieName, cookie);
			} else {
				add(cookieName, "", 0);
			}
		}
		return this;
	}

	public Cookies removeAll() {
		Collection<Cookie> cs = cookies.values();
		for (Cookie c : cs) {
			c.setMaxAge(0);
			cookies.put(c.getName(), c);
		}
		return this;
	}

	public Boolean exist(String cookieName) {
		return cookies.containsKey(cookieName);
	}

	/**
	 * 将cookie刷新到response
	 */
	public void flushTo(HttpServletResponse response) {
		cookies.values().forEach(c -> {
			response.addCookie(c);
		});

	}

}
