package cn.cerestech.framework.support.requirejs.jsmodule;

import java.util.List;

public interface JsModule {

	String getJsUri();

	List<String> getDeps();
}
