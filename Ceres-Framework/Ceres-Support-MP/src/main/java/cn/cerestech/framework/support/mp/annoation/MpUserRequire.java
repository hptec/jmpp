package cn.cerestech.framework.support.mp.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.cerestech.framework.support.mp.enums.AuthorizeScope;
import cn.cerestech.framework.support.mp.mpapi.OauthAPI;

/**
 * 包括类注解
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月6日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface MpUserRequire {
	boolean require() default true;
	boolean force() default false;
	AuthorizeScope scope() default AuthorizeScope.SNSAPI_BASE;
}
