package cn.cerestech.framework.support.requirejs.entity;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.JavaNamingConventer;
import cn.cerestech.framework.support.requirejs.enums.RequireJsUriMapping;

public class RequireJsModule_bak {

	private static Map<String, String> pathMap = Maps.newHashMap();

	private String name;

	private List<String> deps = Lists.newArrayList();

	private String exports;

	private RequireJsModule_bak() {

	}

	public static Map<String, String> pathMap() {
		return pathMap;
	}

	public static RequireJsModule_bak on(String name) {
		RequireJsModule_bak m = new RequireJsModule_bak();
		m.name = name;
		return m;
	}

	public RequireJsModule_bak path(RequireJsUriMapping uriMapping) {
		pathMap.put(uriMapping.name(), uriMapping.uri());
		return this;
	}

	public RequireJsModule_bak deps(RequireJsUriMapping... deps) {
		for (RequireJsUriMapping mapping : deps) {
			String name = JavaNamingConventer.from(mapping.name()).toHyphen();
			this.deps.add(name);
//			pathMap.put(name, mapping.uri());// 按照使用需求注入路径声明
		}
		return this;
	}

	public RequireJsModule_bak deps(String... deps) {
		for (String mapping : deps) {
			this.deps.add(mapping);
		}
		return this;
	}

	public RequireJsModule_bak deps(RequireJsModule_bak... deps) {
		for (RequireJsModule_bak mapping : deps) {
			this.deps.add(mapping.name());
		}
		return this;
	}

	public String name() {
		return name;
	}

	public RequireJsModule_bak name(String name) {
		this.name = name;
		return this;
	}

	public List<String> getDeps() {
		return deps;
	}

	public String exports() {
		return exports;
	}

	public RequireJsModule_bak exports(String exports) {
		this.exports = exports;
		return this;
	}

}
