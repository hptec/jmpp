package cn.cerestech.framework.web.front;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import cn.cerestech.framework.web.support.WebSupport;

public class WebFrontSupport extends WebSupport {

	protected Long userId() {
		return (Long) session(FrontLoginIcptr.SK_FRONT_USER_ID);
	}

	protected String getRequestUrl() {
		HttpServletRequest request = getRequest();
		String encoding = request.getCharacterEncoding();
		if(StringUtils.isBlank(encoding)){
			encoding = "UTF-8";
		}
		String url = "";
		// url+=""; // 请求服务器
		url += request.getRequestURI(); // 工程名
		if (request.getQueryString() != null) { // 判断请求参数是否为空
			url += "?" + request.getQueryString();
		}// 参数
		try {
			return URLEncoder.encode(url,encoding);
		} catch (UnsupportedEncodingException e) {
			try {
				return URLEncoder.encode(url,"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				return url;
			}
		}
	}
}
