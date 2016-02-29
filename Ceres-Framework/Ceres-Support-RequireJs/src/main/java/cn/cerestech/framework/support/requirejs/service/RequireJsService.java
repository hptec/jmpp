package cn.cerestech.framework.support.requirejs.service;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.components.ComponentDispatcher;
import cn.cerestech.framework.support.requirejs.annotation.BootCdnPath;
import cn.cerestech.framework.support.requirejs.annotation.BootCdnPaths;
import cn.cerestech.framework.support.requirejs.annotation.ClasspathPath;
import cn.cerestech.framework.support.requirejs.annotation.ClasspathPaths;
import cn.cerestech.framework.support.requirejs.annotation.RequireJsModule;
import cn.cerestech.framework.support.requirejs.jsmodule.JsModule;
import cn.cerestech.support.classpath.ClasspathService;

@Service
public class RequireJsService implements ComponentDispatcher {

	private Logger log = LogManager.getLogger();

	private static Map<String, KV> shimModules = Maps.newHashMap();

	private static Map<String, String> pathsMap = Maps.newHashMap();

	@Autowired
	private ClasspathService classpathService;

	@Override
	public void recive(String beanName, Object bean) {
		// // 识别模块provider
		// if (bean instanceof RequireJsModuleProvider) {
		// RequireJsModuleProvider provider = (RequireJsModuleProvider) bean;
		// provider.get().forEach(m -> {
		// if (shimModules.get(m.name()) != null) {
		// throw new IllegalArgumentException("RequireJsModule [" + m.name() +
		// "] already exist");
		// } else {
		// shimModules.put(m.name(), m);
		// }
		// });
		// }

		// 识别 BootCdn js库定义
		if (bean.getClass().isAnnotationPresent(BootCdnPaths.class)
				|| bean.getClass().isAnnotationPresent(BootCdnPath.class)) {
			log.trace("found BootCdnRepositories: " + bean.getClass().getCanonicalName());
			List<BootCdnPath> repoList = Lists.newArrayList();
			BootCdnPath repo = bean.getClass().getAnnotation(BootCdnPath.class);
			if (repo != null) {
				repoList.add(repo);
			}
			BootCdnPaths repos = bean.getClass().getAnnotation(BootCdnPaths.class);
			if (repos != null) {
				BootCdnPath[] arr = repos.value();
				for (BootCdnPath r : arr) {
					repoList.add(r);
				}
			}

			// 添加到库
			repoList.forEach(r -> {
				// 如果没有指定ID则使用module name
				putPaths(Strings.isNullOrEmpty(r.id()) ? r.module() : r.id(), bootcdnUri(r));
				if (log.isTraceEnabled()) {
					log.trace((Strings.isNullOrEmpty(r.id()) ? r.module() : r.id()) + " : " + bootcdnUri(r));
				}
			});

		}

		// 识别 Classpath js库定义
		if (bean.getClass().isAnnotationPresent(ClasspathPaths.class)
				|| bean.getClass().isAnnotationPresent(ClasspathPath.class)) {
			log.trace("found ClasspathRepositories: " + bean.getClass().getCanonicalName());
			List<ClasspathPath> repoList = Lists.newArrayList();
			ClasspathPath repo = bean.getClass().getAnnotation(ClasspathPath.class);
			if (repo != null) {
				repoList.add(repo);
			}
			ClasspathPaths repos = bean.getClass().getAnnotation(ClasspathPaths.class);
			if (repos != null) {
				ClasspathPath[] arr = repos.value();
				for (ClasspathPath r : arr) {
					repoList.add(r);
				}
			}

			// 添加到库
			repoList.forEach(r -> {
				putPaths(r.id(), classpathService.visitUri(r.uri()));
			});

		}
		if (bean.getClass().isAnnotationPresent(RequireJsModule.class)) {
			if (bean instanceof JsModule) {
				RequireJsModule anno = bean.getClass().getAnnotation(RequireJsModule.class);
				JsModule jsModule = (JsModule) bean;
				KV module = KV.on();
				List<String> deps = jsModule.getDeps();
				if (deps != null && !deps.isEmpty()) {
					module.put("deps", deps);
				}
				if (!Strings.isNullOrEmpty(anno.export())) {
					module.put("exports", anno.export());
				}
				// 如果指定jsUri则，需要添加到path中
				if (!Strings.isNullOrEmpty(jsModule.getJsUri())) {
					putPaths(anno.id(), jsModule.getJsUri());
				}
				shimModules.put(anno.id(), module);
			}
		}

	}

	@Override
	public void onComplete() {
	}

	public Map<String, KV> getShim() {
		return shimModules;
	}

	public Map<String, String> getPaths() {
		return pathsMap;
	}

	public void putPaths(String id, String uri) {
		if (pathsMap.containsKey(id)) {
			throw new IllegalArgumentException("ReqiureJs paths conflict error: [" + id + "] " + uri);
		} else {
			pathsMap.put(id, uri);
		}
	}

	private String bootcdnUri(BootCdnPath repo) {
		StringBuffer buffer = new StringBuffer("//cdn.bootcss.com/");
		buffer.append(repo.module() + '/');
		buffer.append(repo.version());
		return buffer.toString();
	}

}
