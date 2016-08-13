package cn.cerestech.framework.support.theme.provider;

import org.springframework.beans.factory.annotation.Autowired;

import cn.cerestech.framework.support.classpath.service.ClasspathService;

public abstract class ClasspathStaticThemeProvider implements StaticThemeProvider {

	@Autowired
	ClasspathService classpathService;

	@Override
	public String get(String name) {
		return new String(classpathService.findByUri(getThemeRoot() + name));
	}

}
