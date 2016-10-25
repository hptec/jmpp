package cn.cerestech.framework.support.starter.operator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
		T value = (T) getRequest().getParameter(key);
		if (value == null) {
			value = (T) getRequest().getAttribute(key);
		}
		return value;
	}

	default void putRequest(String name, Object o) {
		getRequest().setAttribute(name, o);
	}

	default void removeRequest(String key) {
		getRequest().removeAttribute(key);
	}

	/**
	 * 从Post中读取文本数据
	 * 
	 * @return
	 */
	default String readPostString() {
		StringBuffer sbf = new StringBuffer("");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(getRequest().getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				sbf.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sbf.toString();
	}

}
