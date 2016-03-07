package cn.cerestech.support.web.console.menu;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Menu {
	String caption() default "页面菜单";

	String icon() default "fa fa-cube";

	String parent() default "";

	int sort() default Integer.MAX_VALUE;

	String key() default "";
}
