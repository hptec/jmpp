package cn.cerestech.framework.support.requirejs.jsmodule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import cn.cerestech.support.classpath.ClasspathService;

public abstract class AbstractJsModule implements JsModule {
	@Autowired
	ClasspathService classpathService;

	public String getJsUri() {
		return "";
	}

	public List<String> getDeps() {
		return Lists.newArrayList();
	}

	protected String bootcdn(String module, String version, String filepath) {
		StringBuffer buffer = new StringBuffer("//cdn.bootcss.com/");
		buffer.append(module + '/');
		buffer.append(version + '/');
		buffer.append(filepath);
		return buffer.toString();
	}

	protected String classpath(String filepath) {
		return classpathService.visitUri(filepath);
	}

}
