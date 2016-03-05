package cn.cerestech.console.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.cerestech.console.enums.LoginRedirectType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface LoginRequired {
	boolean required() default true;

	LoginRedirectType redirectType() default LoginRedirectType.UNKNOWN;
}
