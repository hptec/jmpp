package cn.cerestech.middleware.weixin.mp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.cerestech.middleware.weixin.mp.enums.AuthorizeScope;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface MpUserRequired {
	/**
	 * 转向情况
	 * @return
	 */
	Level required() default Level.NORMAL;
	
	/**
	 * 在 required 满足的情况下，数据存在也要求强制转向
	 * @return
	 */
	boolean force() default false;
	
	/**
	 * 是否是js调用，js 调用只返回“flush_page” 字符串，告诉前端要重新刷新页面获取微信认证
	 * @return
	 */
	boolean forJs() default false;

	/**
	 * 专线该请求的方式
	 * @return
	 */
	AuthorizeScope type() default AuthorizeScope.SNSAPI_BASE;

	public enum Level {
		BROWSER_SUPPORT, // 浏览器支持则做微信转向，此时不强制
		NORMAL// 如果缓存存在则不进行，否则进行转向
	}	
}