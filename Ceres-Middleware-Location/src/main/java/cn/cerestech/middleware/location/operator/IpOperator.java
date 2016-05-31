package cn.cerestech.middleware.location.operator;

import javax.servlet.http.HttpServletRequest;

import cn.cerestech.framework.support.web.operator.RequestOperator;
import cn.cerestech.middleware.location.ip.IP;

public interface IpOperator extends RequestOperator {
	default IP getIp() {
		HttpServletRequest request = getRequest();
		if (request == null) {
			return new IP();
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
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return IP.fromString(ip == null ? "127.0.0.1" : ip);
	}
}
