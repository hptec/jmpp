package cn.cerestech.framework.support.requirejs.entity;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.JavaNamingConventer;
import cn.cerestech.framework.support.requirejs.enums.RequireJsUriMapping;

public class RequireJsModule {

	private static Map<String, String> pathMap = Maps.newHashMap();

	private String name;

	private List<String> deps = Lists.newArrayList();

	private String exports;

	private RequireJsModule() {

	}

	public static Map<String, String> pathMap() {
		return pathMap;
	}

	public static RequireJsModule on(String name) {
		RequireJsModule m = new RequireJsModule();
		m.name = name;
		return m;
	}

	public RequireJsModule path(RequireJsUriMapping uriMapping) {
		pathMap.put(uriMapping.name(), uriMapping.uri());
		return this;
	}

	public RequireJsModule deps(RequireJsUriMapping... deps) {
		for (RequireJsUriMapping mapping : deps) {
			String name = JavaNamingConventer.from(mapping.name()).toHyphen();
			this.deps.add(name);
//			pathMap.put(name, mapping.uri());// 按照使用需求注入路径声明
		}
		return this;
	}

	public RequireJsModule deps(String... deps) {
		for (String mapping : deps) {
			this.deps.add(mapping);
		}
		return this;
	}

	public RequireJsModule deps(RequireJsModule... deps) {
		for (RequireJsModule mapping : deps) {
			this.deps.add(mapping.name());
		}
		return this;
	}

	public String name() {
		return name;
	}

	public RequireJsModule name(String name) {
		this.name = name;
		return this;
	}

	public List<String> getDeps() {
		return deps;
	}

	public String exports() {
		return exports;
	}

	public RequireJsModule exports(String exports) {
		this.exports = exports;
		return this;
	}

}
