package cn.cerestech.framework.support.requirejs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

import cn.cerestech.framework.support.application.enums.ApplicationCategory;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RequireJsApplicationBootstrapJs {
	ApplicationCategory forCategory();

	String jsUri();
}
