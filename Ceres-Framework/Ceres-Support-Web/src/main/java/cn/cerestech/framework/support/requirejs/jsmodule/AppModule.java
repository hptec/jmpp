package cn.cerestech.framework.support.requirejs.jsmodule;

import cn.cerestech.framework.support.requirejs.annotation.RequireJsModule;

@RequireJsModule(id = "app", export = "app")
public class AppModule extends AbstractJsModule implements JsModule {

	@Override
	public String getJsUri() {
		return classpath("support/web/js/app");
	}
}
