package cn.cerestech.framework.support.requirejs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Component
@Repeatable(BootCdnPaths.class)
public @interface BootCdnPath {
	String id() default "";

	String module();

	String version();

	/**
	 * 用于表示是否需要在启动angular时注入模块依赖及标识依赖模块的名称
	 * 
	 * @return
	 */
	String angularRequiredModule() default "";
}
