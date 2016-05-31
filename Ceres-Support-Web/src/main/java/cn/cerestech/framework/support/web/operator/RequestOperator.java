package cn.cerestech.framework.support.web.operator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 操作Request相关类
 * 
 * @author harryhe
 *
 */
public interface RequestOperator {

	default HttpServletRequest getRequest() {
		HttpServletRequest request = null;
		RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
		if (attrs != null) {
			request = ((ServletRequestAttributes) attrs).getRequest();
		}
		return request;
	}

	@SuppressWarnings("unchecked")
	default <T> T getRequest(String key) {
		return (T) getRequest().getParameter(key);
	}

	default void putRequest(String name, Object o) {
		getRequest().setAttribute(name, o);
	}

	default void removeRequest(String key) {
		getRequest().removeAttribute(key);
	}

}
