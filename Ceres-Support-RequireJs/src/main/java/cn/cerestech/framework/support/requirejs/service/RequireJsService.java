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

import cn.cerestech.framework.core.components.ComponentDispatcher;
import cn.cerestech.framework.support.application.enums.ApplicationCategory;
import cn.cerestech.framework.support.requirejs.RequireJsApplicationBootstrapJs;
import cn.cerestech.framework.support.requirejs.annotation.BootCdnRepositories;
import cn.cerestech.framework.support.requirejs.annotation.BootCdnRepository;
import cn.cerestech.framework.support.requirejs.annotation.ClasspathRepositories;
import cn.cerestech.framework.support.requirejs.annotation.ClasspathRepository;
import cn.cerestech.framework.support.requirejs.entity.RequireJsModule;
import cn.cerestech.framework.support.requirejs.provider.RequireJsModuleProvider;
import cn.cerestech.support.classpath.ClasspathService;

@Service
public class RequireJsService implements ComponentDispatcher {

	private Logger log = LogManager.getLogger();

	private static Map<String, RequireJsModule> shimModules = Maps.newHashMap();

	private static Map<String, String> pathsMap = Maps.newHashMap();

	private static Map<ApplicationCategory, String> starterJsMap = Maps.newHashMap();

	@Autowired
	private ClasspathService classpathService;

	@Override
	public void recive(String beanName, Object bean) {
		// 识别模块provider
		if (bean instanceof RequireJsModuleProvider) {
			RequireJsModuleProvider provider = (RequireJsModuleProvider) bean;
			provider.get().forEach(m -> {
				if (shimModules.get(m.name()) != null) {
					throw new IllegalArgumentException("RequireJsModule [" + m.name() + "] already exist");
				} else {
					shimModules.put(m.name(), m);
				}
			});
		}

		// 识别启动js
		if (bean.getClass().isAnnotationPresent(RequireJsApplicationBootstrapJs.class)) {
			log.trace("found require js starter: " + bean.getClass().getCanonicalName());

			RequireJsApplicationBootstrapJs bootJs = bean.getClass()
					.getAnnotation(RequireJsApplicationBootstrapJs.class);
			if (starterJsMap.get(bootJs.forCategory()) != null) {
				// 避免重复设定
				throw new IllegalArgumentException(
						"Bootstrap Js Conflict: [" + bootJs.forCategory() + "] uri:" + bootJs.jsUri());
			} else {
				starterJsMap.put(bootJs.forCategory(), classpathService.visitUri(bootJs.jsUri()));
			}
		}

		// 识别 BootCdn js库定义
		if (bean.getClass().isAnnotationPresent(BootCdnRepositories.class)
				|| bean.getClass().isAnnotationPresent(BootCdnRepository.class)) {
			log.trace("found BootCdnRepositories: " + bean.getClass().getCanonicalName());
			List<BootCdnRepository> repoList = Lists.newArrayList();
			BootCdnRepository repo = bean.getClass().getAnnotation(BootCdnRepository.class);
			if (repo != null) {
				repoList.add(repo);
			}
			BootCdnRepositories repos = bean.getClass().getAnnotation(BootCdnRepositories.class);
			if (repos != null) {
				BootCdnRepository[] arr = repos.value();
				for (BootCdnRepository r : arr) {
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
		if (bean.getClass().isAnnotationPresent(ClasspathRepositories.class)
				|| bean.getClass().isAnnotationPresent(ClasspathRepository.class)) {
			log.trace("found ClasspathRepositories: " + bean.getClass().getCanonicalName());
			List<ClasspathRepository> repoList = Lists.newArrayList();
			ClasspathRepository repo = bean.getClass().getAnnotation(ClasspathRepository.class);
			if (repo != null) {
				repoList.add(repo);
			}
			ClasspathRepositories repos = bean.getClass().getAnnotation(ClasspathRepositories.class);
			if (repos != null) {
				ClasspathRepository[] arr = repos.value();
				for (ClasspathRepository r : arr) {
					repoList.add(r);
				}
			}

			// 添加到库
			repoList.forEach(r -> {
				putPaths(r.id(), classpathService.visitUri(r.uri()));
			});

		}

	}

	@Override
	public void onComplete() {
	}

	public Map<String, RequireJsModule> getShim() {
		return shimModules;
	}

	public Map<String, String> getPaths() {
		return pathsMap;
	}

	public Map<ApplicationCategory, String> getStarter() {
		return starterJsMap;
	}

	public void putPaths(String id, String uri) {
		if (pathsMap.containsKey(id)) {
			throw new IllegalArgumentException("ReqiureJs paths conflict error: [" + id + "] " + uri);
		} else {
			pathsMap.put(id, uri);
		}
	}

	private String bootcdnUri(BootCdnRepository repo) {
		StringBuffer buffer = new StringBuffer("//cdn.bootcss.com/");
		buffer.append(repo.module() + '/');
		buffer.append(repo.version());
		return buffer.toString();
	}

}
