package cn.cerestech.framework.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 重写Spring注入类
 *
 */
public class SpringUtil implements ApplicationContextAware {
	public static ApplicationContext ctx;

	public synchronized void setApplicationContext(ApplicationContext arg0) throws BeansException {
		ctx = arg0;
	}

	/**
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		return ctx.getBean(beanName);
	}

	public static <T> T getBean(Class<T> requiredType) {
		return ctx.getBean(requiredType);
	}

}
