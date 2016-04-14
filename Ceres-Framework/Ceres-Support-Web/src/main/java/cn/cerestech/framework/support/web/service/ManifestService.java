package cn.cerestech.framework.support.web.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.gson.JsonElement;

import cn.cerestech.framework.core.Core;
import cn.cerestech.framework.core.components.ComponentDispatcher;
import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.web.annotation.Manifest;

@Service
public class ManifestService implements ComponentDispatcher {

	private Logger log = LogManager.getLogger();

	private Map<String, Jsons> manifestPaths = Maps.newHashMap();

	// 数据缓存
	List<Jsons> cacheManifests = Lists.newArrayList();
	Map<PlatformCategory, List<JsonElement>> cacheJsModules = Maps.newHashMap();
	Map<PlatformCategory, JsonElement> cacheStarterMap = Maps.newHashMap();
	Map<String, String> cachePathsMap = Maps.newHashMap();
	Map<PlatformCategory, List<JsonElement>> cachePages = Maps.newHashMap();

	@Override
	public void recive(String beanName, Object bean) {
		if (bean.getClass().isAnnotationPresent(Manifest.class)) {
			Manifest mf = bean.getClass().getAnnotation(Manifest.class);
			if (mf != null) {
				String path = mf.value();
				if (Strings.isNullOrEmpty(path)) {
					log.warn("Manifest from " + bean.getClass().getCanonicalName() + " is empty");
				} else {
					log.trace("Found manifest json: " + path);
					try {
						String content = Resources.toString(Resources.getResource(path), Core.charset());
						manifestPaths.put(path, Jsons.from(content));
					} catch (IOException e) {
						log.catching(e);
					}
				}

			}
		}
	}

	public List<Jsons> allManifest() {
		if (cacheManifests.isEmpty()) {
			cacheManifests.addAll(manifestPaths.values());
		}
		return cacheManifests;
	}

	private List<Jsons> findElement(String nodeName, PlatformCategory category) {
		List<Jsons> list = Lists.newArrayList();
		// 找到应用于所有的
		allManifest().forEach(manifest -> {
			Jsons json = manifest.get(nodeName);
			if (json.isObject()) {
				list.add(json);
			} else if (json.isArray()) {
				list.addAll(json.asList());
			}
		});
		// 找到应用于特定platform的
		if (category != null) {
			allManifest().forEach(manifest -> {
				Jsons json = manifest.get(nodeName + "_" + category.key());
				if (json.isObject()) {
					list.add(json);
				} else if (json.isArray()) {
					list.addAll(json.asList());
				}
			});
		}
		return list;
	}

	public List<JsonElement> getPages(PlatformCategory category) {
		if (category == null) {
			throw new IllegalArgumentException("PlatformCategory is required");
		}

		if (!cachePages.containsKey(category)) {
			List<JsonElement> cateList = Lists.newArrayList();
			findElement("pages", category).forEach(pg -> {
				cateList.add(pg.getRoot());
			});
			cachePages.put(category, cateList);
		}

		return cachePages.get(category);
	}

	public List<JsonElement> getJsModules(PlatformCategory category) {
		if (category == null) {
			throw new IllegalArgumentException("PlatformCategory is required");
		}
		if (!cacheJsModules.containsKey(category)) {
			Map<String, JsonElement> moduleMap = Maps.newHashMap();
			List<String> conflict = Lists.newArrayList();

			findElement("jsModules", category).forEach(m -> {
				String name = m.get("name").asString();
				if (moduleMap.containsKey(name)) {
					conflict.add(m.toPrettyJson());
				} else {
					moduleMap.put(name, m.getRoot());
				}
			});

			// 检测是否有冲突
			if (!conflict.isEmpty()) {
				throw new IllegalArgumentException(Jsons.from(conflict).toPrettyJson());
			}

			cacheJsModules.put(category, moduleMap.values().stream().collect(Collectors.toList()));

			// 检测是否有依赖未注入
			List<String> noDeps = Lists.newArrayList();
			cacheJsModules.get(category).forEach(root -> {
				Jsons json = Jsons.from(root);
				json.get("deps").asList().forEach(str -> {
					if (!moduleMap.containsKey(str.asString()) && str.asString().length() < 30) {
						// 大于30用于过滤链接类的
						noDeps.add(str.asString());
					}
				});
			});

			if (!noDeps.isEmpty()) {
				throw new IllegalArgumentException("The jsModule not exist: \n" + Jsons.from(noDeps).toPrettyJson());
			}

			log.trace("Modules: \n" + Jsons.from(cacheJsModules).toPrettyJson());
		}
		return cacheJsModules.get(category);

	}

	/**
	 * 获取启动器
	 * 
	 * @param category2
	 * 
	 * @return
	 */
	public JsonElement getStarters(PlatformCategory category) {
		if (category == null) {
			throw new IllegalArgumentException("PlatformCategory is required");
		}
		if (!cacheStarterMap.containsKey(category)) {
			findElement("starter", category).forEach(starter -> {
				String platformStr = starter.get("platform").asString();
				if (!Strings.isNullOrEmpty(platformStr) && category.key().equals(platformStr)) {
					if (cacheStarterMap.containsKey(platformStr)) {
						throw new IllegalArgumentException(
								"The Starter for " + platformStr + " conflict: \n" + starter.toPrettyJson());
					} else {
						cacheStarterMap.put(category, starter.getRoot());
					}
				}
			});
		}

		return cacheStarterMap.get(category);
	}

	@Override
	public void onComplete() {
	}

}
