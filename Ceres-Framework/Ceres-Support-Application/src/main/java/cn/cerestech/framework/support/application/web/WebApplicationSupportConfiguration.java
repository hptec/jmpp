package cn.cerestech.framework.support.application.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//暂不保护
//@Configuration
public class WebApplicationSupportConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	WebApplicationSupportInterceptor inteceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(inteceptor).addPathPatterns("/api/**");
		super.addInterceptors(registry);
	}
}
