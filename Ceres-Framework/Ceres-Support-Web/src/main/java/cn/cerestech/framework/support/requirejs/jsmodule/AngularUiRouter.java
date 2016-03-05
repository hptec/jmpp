package cn.cerestech.framework.support.requirejs.jsmodule;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.support.requirejs.annotation.RequireJsModule;

@RequireJsModule(id = "angular-ui-router", angularRequiredModule = "ui.router")
public class AngularUiRouter extends AbstractJsModule implements JsModule {

	@Override
	public String getJsUri() {
		return bootcdn("angular-ui-router", "1.0.0-alpha0", "angular-ui-router.min");
	}

	@Override
	public List<String> getDeps() {
		return Lists.newArrayList("angular");
	}

}
