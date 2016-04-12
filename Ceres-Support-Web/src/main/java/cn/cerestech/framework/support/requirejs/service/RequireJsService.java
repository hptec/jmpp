package cn.cerestech.framework.support.requirejs.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.components.ComponentDispatcher;
import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.support.requirejs.annotation.RequireJsBootstrap;
import cn.cerestech.support.classpath.ClasspathService;

@Service
public class RequireJsService implements ComponentDispatcher {

	private Logger log = LogManager.getLogger();

	private static Map<String, KV> shimModules = Maps.newHashMap();

	private static Map<String, String> pathsMap = Maps.newHashMap();

	private static Set<String> angularRequiredModules = Sets.newHashSet();

	private static Map<PlatformCategory, RequireJsBootstrap> bootMap = Maps.newHashMap();

	@Autowired
	private ClasspathService classpathService;

	@Override
	public void recive(String beanName, Object bean) {

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
					if (!Strings.isNullOrEmpty(r.angularRequiredModule())) {
						angularRequiredModules.add(r.angularRequiredModule());
					}
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
					if (!Strings.isNullOrEmpty(r.angularRequiredModule())) {
						angularRequiredModules.add(r.angularRequiredModule());
					}
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
				if (!Strings.isNullOrEmpty(anno.angularRequiredModule())) {
					angularRequiredModules.add(anno.angularRequiredModule());
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

	public Set<String> getAngularRequiredModules() {
		return angularRequiredModules;
	}

	public void putPaths(String id, String uri) {
		if (pathsMap.containsKey(id)) {
			throw new IllegalArgumentException("ReqiureJs paths conflict error: [" + id + "] " + uri);
		} else {
			pathsMap.put(id, uri);
		}
	}

	public Map<String, KV> getBootConfig() {
		Map<String, KV> bootConfig = Maps.newHashMap();

		bootMap.forEach((k, b) -> {
			KV boot = KV.on();
			if (b != null) {
				boot.put("bootstrapJs", b.bootstrapJs());
				boot.put("html5mode", b.html5mode());
				boot.put("platform", b.platform().key());
			}
			bootConfig.put(k.key(), boot);
		});

		return bootConfig;
	}

	public void putBoot(PlatformCategory platform, RequireJsBootstrap boot) {
		if (bootMap.containsKey(platform)) {
			throw new IllegalArgumentException("Boot config conflict: " + platform.name());
		}
		bootMap.put(platform, boot);
	}

}
