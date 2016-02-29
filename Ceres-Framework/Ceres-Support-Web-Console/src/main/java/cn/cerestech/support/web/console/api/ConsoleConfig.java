package cn.cerestech.support.web.console.api;

import org.springframework.stereotype.Component;

import cn.cerestech.framework.support.classpath.webapi.ClasspathUriProvider;

@Component
public class ConsoleConfig implements ClasspathUriProvider {

	@Override
	public String defineUri() {
		return "support/web/console/js/config.json";
	}

	@Override
	public String getContent() {
		return "{config:1}";
	}

}
