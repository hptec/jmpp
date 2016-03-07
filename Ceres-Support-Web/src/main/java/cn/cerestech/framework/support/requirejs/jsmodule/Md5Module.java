package cn.cerestech.framework.support.requirejs.jsmodule;

import cn.cerestech.framework.support.requirejs.annotation.RequireJsModule;

@RequireJsModule(id = "md5", export = "md5")
public class Md5Module extends AbstractJsModule implements JsModule {

	@Override
	public String getJsUri() {
		return bootcdn("blueimp-md5", "2.3.0", "js/md5.min.js");
	}

}
