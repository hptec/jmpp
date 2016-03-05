package cn.cerestech.framework.support.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class WebUtils {

	public static String getCurrentIp() {
		HttpServletRequest request = getCurrentRequest();
		if (request == null) {
			return "";
		}
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
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
			ip = request.getRemoteAddr();
		}
		return ip == "" ? "127.0.0.1" : ip;
	}

	public static HttpServletRequest getCurrentRequest() {
		HttpServletRequest request = null;
		RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
		if (attrs != null) {
			request = ((ServletRequestAttributes) attrs).getRequest();
		}
		return request;
	}

	public static HttpServletResponse getCurrentResponse() {
		HttpServletResponse response = null;
		RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
		if (attrs != null) {
			response = ((ServletRequestAttributes) attrs).getResponse();
		}
		return response;
	}

}
