package cn.cerestech.framework.support.requirejs.jsmodule;

import cn.cerestech.framework.support.requirejs.annotation.RequireJsModule;

@RequireJsModule(id = "angular-local")
public class AngularLocalModule extends AbstractJsModule implements JsModule {

	@Override
	public String getJsUri() {
		return bootcdn("angular.js", "2.2.1", "i18n/angular-locale_zh-cn");
	}

}
