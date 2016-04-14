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
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.web.annotation.Manifest;

@Service
public class ManifestService implements ComponentDispatcher {

	private Logger log = LogManager.getLogger();

	private Map<String, Jsons> manifestPaths = Maps.newHashMap();

	// 数据缓存
	List<Jsons> cacheManifests = Lists.newArrayList();
	List<JsonElement> cacheJsModules = Lists.newArrayList();
	Map<String, JsonElement> cacheStarterMap = Maps.newHashMap();
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

	// private void putPageCache(PlatformCategory category, JsonElement page) {
	// if (category == null) {
	// // 全部添加
	// PlatformCategory[] cates = PlatformCategory.values();
	// for (PlatformCategory c : cates) {
	// if (!cachePages.containsKey(c)) {
	// cachePages.put(c, Lists.newArrayList());
	// }
	// cachePages.get(c).add(page);
	// }
	// } else {
	// // 添加到指定
	// if (!cachePages.containsKey(category)) {
	// cachePages.put(category, Lists.newArrayList());
	// }
	// cachePages.get(category).add(page);
	// }
	// }

	public List<JsonElement> getJsModules(PlatformCategory category) {
		if (cacheJsModules.isEmpty()) {
			Map<String, JsonElement> moduleMap = Maps.newHashMap();
			List<String> conflict = Lists.newArrayList();

			allManifest().forEach(json -> {
				json.get("jsModules").asList().forEach(m -> {
					String name = m.get("name").asString();
					if (moduleMap.containsKey(name)) {
						conflict.add(m.toPrettyJson());
					} else {
						moduleMap.put(name, m.getRoot());
					}
				});
			});

			// 检测是否有冲突
			if (!conflict.isEmpty()) {
				throw new IllegalArgumentException(Jsons.from(conflict).toPrettyJson());
			}

			cacheJsModules = moduleMap.values().stream().collect(Collectors.toList());

			// 检测是否有依赖未注入
			List<String> noDeps = Lists.newArrayList();
			cacheJsModules.forEach(root -> {
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
		return cacheJsModules;

	}

	/**
	 * 获取启动器
	 * 
	 * @param category2
	 * 
	 * @return
	 */
	public JsonElement getStarters(PlatformCategory category2) {
		if (cacheStarterMap.isEmpty()) {
			allManifest().forEach(json -> {
				Jsons starter = json.get("starter");
				String platformStr = starter.get("platform").asString();
				if (!Strings.isNullOrEmpty(platformStr)) {
					PlatformCategory category = EnumCollector.forClass(PlatformCategory.class).keyOf(platformStr);
					if (category != null) {
						if (cacheStarterMap.containsKey(platformStr)) {
							throw new IllegalArgumentException(
									"The Starter for " + platformStr + " conflict: \n" + starter.toPrettyJson());
						} else {
							cacheStarterMap.put(platformStr, starter.getRoot());
						}
					}
				}
			});
		}

		return cacheStarterMap.get(category2.key());
	}

	// public Map<String, String> getPaths() {
	// if (cachePathsMap.isEmpty()) {
	// allManifest().forEach(json -> {
	// Jsons paths = json.get("paths");
	// if (paths.getRoot() != null) {
	// paths.getRoot().getAsJsonObject().entrySet().forEach(entry -> {
	// Jsons v = Jsons.from(entry.getValue());
	// cachePathsMap.put(entry.getKey(), v.asString());
	// });
	// }
	// });
	// log.trace("发现Paths:\n" + Jsons.from(cachePathsMap).toPrettyJson());
	// }
	// return cachePathsMap;
	// }

	@Override
	public void onComplete() {
	}

}
