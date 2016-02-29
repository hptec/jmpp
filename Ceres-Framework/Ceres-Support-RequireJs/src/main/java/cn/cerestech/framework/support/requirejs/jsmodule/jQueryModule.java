package cn.cerestech.framework.support.requirejs.jsmodule;

import cn.cerestech.framework.support.requirejs.annotation.RequireJsModule;

@RequireJsModule(id = "jquery", export = "$")
public class jQueryModule extends AbstractJsModule implements JsModule {

	@Override
	public String getJsUri() {
		return bootcdn("jquery", "2.2.1", "jquery.min");
	}
}
