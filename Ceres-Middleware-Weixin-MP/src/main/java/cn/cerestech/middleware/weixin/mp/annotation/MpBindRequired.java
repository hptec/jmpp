package cn.cerestech.middleware.weixin.mp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface MpBindRequired {
	/**
	 * 是否需要绑定
	 * @return
	 */
	boolean required() default true;
	
	/**
	 * 跳转到的链接
	 * @return
	 */
	String uri() default "";
}
