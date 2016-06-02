package cn.cerestech.framework.platform.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class PlatformInterceptorAdaptor extends WebMvcConfigurerAdapter {

	@Autowired
	PlatformInterceptor platformInterceptor;

	/**
	 * 配置拦截器
	 * 
	 * @author lance
	 * @param registry
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(platformInterceptor).addPathPatterns("/**");
	}

}
