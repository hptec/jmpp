package cn.cerestech.support.web.console.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Component
@Repeatable(ConsoleModule.class)
public @interface ModulePage {

	/**
	 * 是否是模块的主入口页面
	 * 
	 * @return
	 */
	boolean entry() default false;

	String uri() default "";

	String pattern() default "";

	String tpl() default "";

	String ctrl() default "";
}
