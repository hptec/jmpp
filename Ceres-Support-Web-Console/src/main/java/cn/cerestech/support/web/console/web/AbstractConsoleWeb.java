package cn.cerestech.support.web.console.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.io.Files;

import cn.cerestech.framework.support.login.interceptor.LoginInterceptor;
import cn.cerestech.framework.support.web.ContentType;
import cn.cerestech.framework.support.web.WebSupport;
import cn.cerestech.support.classpath.ClasspathService;

public class AbstractConsoleWeb extends WebSupport {

	protected static final String THEME_PATH_PREFIX = "support/web/console/theme/";

	@Autowired
	protected ClasspathService classpathService;

	protected String getTheme() {
		return "homer";
	}

	protected byte[] themeResource(String relativeUri) {
		String fullUri = THEME_PATH_PREFIX + getTheme() + "/" + relativeUri;
		return classpathService.findByUri(fullUri);
	}

	protected void themeOut(String relativeUri) {
		zipOut(themeResource(relativeUri), ContentType.getByExtension(Files.getFileExtension(relativeUri)));
	}

	protected Long getUserId() {
		return (Long) session(LoginInterceptor.SESSION_LOGINENTITY_ID + getPlatformCategory().key());
	}
}
