package cn.cerestech.framework.support.mp.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月7日
 */
@Configuration
public class MpInterceptorAdapter extends WebMvcConfigurerAdapter {

	@Autowired
	MpInterceptor mpInterceptor;

	/**
	 * 配置拦截器
	 * 
	 * @author lance
	 * @param registry
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(mpInterceptor).addPathPatterns("/**");
	}

}
