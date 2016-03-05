package cn.cerestech.framework.support.login.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.cerestech.framework.core.enums.PlatformCategory;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface LoginProvider {
	PlatformCategory value();
}
