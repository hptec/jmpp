package cn.cerestech.framework.support.requirejs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RequireJsModule {
	String id();

	String export() default "";

	/**
	 * 用于表示是否需要在启动angular时注入模块依赖及标识依赖模块的名称
	 * 
	 * @return
	 */
	String angularRequiredModule() default "";
}
