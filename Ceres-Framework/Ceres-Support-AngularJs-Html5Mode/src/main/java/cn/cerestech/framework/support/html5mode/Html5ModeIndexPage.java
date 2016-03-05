package cn.cerestech.framework.support.html5mode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.cerestech.framework.platform.enums.PlatformCategory;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Html5ModeIndexPage {
	PlatformCategory value();
}
