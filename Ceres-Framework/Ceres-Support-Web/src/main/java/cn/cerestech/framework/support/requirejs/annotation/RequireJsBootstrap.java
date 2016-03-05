package cn.cerestech.framework.support.requirejs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

import cn.cerestech.framework.core.enums.PlatformCategory;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RequireJsBootstrap {
	PlatformCategory platform();

	String bootstrapJs();

	boolean html5mode() default true;
}
