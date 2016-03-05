package cn.cerestech.framework.support.requirejs.jsmodule;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.support.requirejs.annotation.RequireJsModule;

@RequireJsModule(id = "bootstrap")
public class BootstrapModule extends AbstractJsModule implements JsModule {

	public static final String MODULE = "bootstrap";
	public static final String VERSION = "3.3.6";

	@Override
	public String getJsUri() {
		return bootcdn(MODULE, VERSION, "js/bootstrap.min");
	}

	@Override
	public List<String> getDeps() {
		return Lists.newArrayList("jquery", "css!" + bootcdn(MODULE, VERSION, "css/bootstrap.min"));
	}

}
