package cn.cerestech.framework.web.front;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface FrontLoginRequired {
	boolean required() default true;
	Device device() default Device.WEB;
}
