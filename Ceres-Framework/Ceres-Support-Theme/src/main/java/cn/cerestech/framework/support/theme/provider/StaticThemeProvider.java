package cn.cerestech.framework.support.theme.provider;

public interface StaticThemeProvider extends ThemeProvider {

	byte[] get(String name);
}
