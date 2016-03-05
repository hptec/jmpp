package cn.cerestech.framework.support.requirejs.jsmodule;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.support.requirejs.annotation.RequireJsModule;

@RequireJsModule(id = "angular-async-loader", export = "asyncLoader1")
public class AngularAsyncLoader extends AbstractJsModule implements JsModule {

	@Override
	public String getJsUri() {
		return classpath("support/requirejs/js/angular-async-loader/angular-async-loader.min");
	}

	@Override
	public List<String> getDeps() {
		return Lists.newArrayList("angular", "angular-ui-router");
	}

}
