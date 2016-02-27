package cn.cerestech.framework.support.requirejs.provider;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import cn.cerestech.framework.support.requirejs.entity.RequireJsModule;
import cn.cerestech.framework.support.requirejs.enums.CoreUriMapping;

@Component
public class CoreModuleProvider implements RequireJsModuleProvider {

	@Override
	public List<RequireJsModule> get() {

		RequireJsModule mJquery = RequireJsModule.on("jquery").exports("$").deps(CoreUriMapping.jquery);
		RequireJsModule mAngular = RequireJsModule.on("angular").exports("angular").deps("require-css");
		RequireJsModule mAngularLocal = RequireJsModule.on("angular-local").deps(CoreUriMapping.angular,
				CoreUriMapping.angularJsLocal);
		RequireJsModule mAngularUiRouter = RequireJsModule.on("angular-ui-router").deps(CoreUriMapping.angular);
		RequireJsModule mAngularAyncLoader = RequireJsModule.on("angular-async-loader").deps(CoreUriMapping.angular)
				.deps(mAngularUiRouter).exports("asyncLoader");
		RequireJsModule mApp = RequireJsModule.on("app").path(CoreUriMapping.app)
				.deps(CoreUriMapping.angularAsyncLoader, CoreUriMapping.angularUiRouter);

		return Lists.newArrayList(mJquery, mAngular, mAngularLocal, mAngularAyncLoader, mAngularUiRouter, mApp);
	}

}
