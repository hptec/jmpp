package cn.cerestech.framework.support.requirejs.jsmodule;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.support.requirejs.annotation.RequireJsModule;

@RequireJsModule(id = "angular", export = "angular")
public class AngularModule extends AbstractJsModule implements JsModule {

	@Override
	public String getJsUri() {
		return bootcdn("angular.js", "1.5.0", "angular");
	}

	@Override
	public List<String> getDeps() {
		return Lists.newArrayList("require-css");
	}

}
