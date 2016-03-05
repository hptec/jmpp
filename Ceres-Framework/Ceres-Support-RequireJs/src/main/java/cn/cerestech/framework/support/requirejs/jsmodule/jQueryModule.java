package cn.cerestech.framework.support.requirejs.jsmodule;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.support.requirejs.annotation.RequireJsModule;

@RequireJsModule(id = "jquery", export = "$")
public class jQueryModule extends AbstractJsModule implements JsModule {

	@Override
	public String getJsUri() {
		return bootcdn("jquery", "2.2.1", "jquery.min");
	}

	@Override
	public List<String> getDeps() {
		return Lists.newArrayList(bootcdn("string.js", "3.3.1", "string.min.js"));
	}
}
