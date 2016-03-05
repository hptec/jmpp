package cn.cerestech.support.web.console.jsmodule;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.support.requirejs.annotation.RequireJsModule;
import cn.cerestech.framework.support.requirejs.jsmodule.AbstractJsModule;
import cn.cerestech.framework.support.requirejs.jsmodule.JsModule;

@RequireJsModule(id = "icheck")
public class iCheckModule extends AbstractJsModule implements JsModule {
	@Override
	public String getJsUri() {
		return bootcdn("iCheck", "1.0.2", "icheck.min");
	}

	@Override
	public List<String> getDeps() {
		return Lists.newArrayList("jquery");
	}

}
