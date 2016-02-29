package cn.cerestech.framework.support.requirejs.provider;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import cn.cerestech.framework.support.requirejs.entity.RequireJsModule_bak;
import cn.cerestech.framework.support.requirejs.enums.CoreUriMapping;

@Component
public class CoreModuleProvider implements RequireJsModuleProvider {

	@Override
	public List<RequireJsModule_bak> get() {

		RequireJsModule_bak mJquery = RequireJsModule_bak.on("jquery").exports("$");
		RequireJsModule_bak mAngular = RequireJsModule_bak.on("angular").exports("angular").deps("require-css");
		RequireJsModule_bak mAngularLocal = RequireJsModule_bak.on("angular-local").deps(CoreUriMapping.angular,
				CoreUriMapping.angularJsLocal);
		RequireJsModule_bak mAngularUiRouter = RequireJsModule_bak.on("angular-ui-router").deps(CoreUriMapping.angular);
		RequireJsModule_bak mAngularAyncLoader = RequireJsModule_bak.on("angular-async-loader").deps(CoreUriMapping.angular)
				.deps(mAngularUiRouter).exports("asyncLoader");
		RequireJsModule_bak mApp = RequireJsModule_bak.on("app").path(CoreUriMapping.app)
				.deps(CoreUriMapping.angularAsyncLoader, CoreUriMapping.angularUiRouter);

		return Lists.newArrayList(mJquery, mAngular, mAngularLocal, mAngularAyncLoader, mAngularUiRouter, mApp);
	}

}
