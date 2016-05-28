package cn.cerestech.framework.support.web.operator;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 操作HttpServletResponse的类
 * 
 * @author harryhe
 *
 */
public interface ResponseOperator {

	/**
	 * 获取当前的Response
	 * 
	 * @return
	 */
	default HttpServletResponse getResponse() {
		HttpServletResponse response = null;
		RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
		if (attrs != null) {
			response = ((ServletRequestAttributes) attrs).getResponse();
		}
		return response;
	}
}
