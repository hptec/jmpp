package cn.cerestech.support.web.console.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ConsoleModule {
	String id() default "";

	String icon() default "fa fa-cube";

	String caption() default "未知菜单";

	int sort() default Integer.MAX_VALUE;

	ModulePage[] value();
}
