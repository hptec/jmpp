package cn.cerestech.framework.support.starter.console.theme.homer.provider;

import org.springframework.stereotype.Component;

import cn.cerestech.framework.support.theme.provider.ClasspathStaticThemeProvider;

@Component
public class HomerThemeProvider extends ClasspathStaticThemeProvider {

	@Override
	public String getThemeRoot() {
		return "support/starter/console/theme/homer/";
	}

}
