package cn.cerestech.framework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

import cn.cerestech.framework.core.enums.PlatformCategory;

/**
 * 标识某个类是框架中的Privder
 * 
 * @author harryhe
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Component
public @interface Provider {

	/**
	 * 应用与哪个平台
	 * 
	 * @return
	 */
	PlatformCategory value();

	/**
	 * 如果为true会强制覆盖掉同类的登录器
	 * 
	 * @return
	 */
	boolean important() default false;
}
