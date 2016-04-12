package cn.cerestech.framework.support.requirejs.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;

import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.service.ManifestService;

@Service
public class DefaultWebService {

	private Logger log = LogManager.getLogger();

	@Autowired
	private ManifestService manifestService;

	public List<JsonElement> getJsModules() {

		Map<String, JsonElement> moduleMap = Maps.newHashMap();
		List<String> conflict = Lists.newArrayList();

		manifestService.allManifest().forEach(json -> {
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

		List<JsonElement> result = moduleMap.values().stream().collect(Collectors.toList());

		// 检测是否有依赖未注入
		List<String> noDeps = Lists.newArrayList();
		result.forEach(root -> {
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

		log.trace("Modules: \n" + Jsons.from(result).toPrettyJson());
		return result;

	}

	/**
	 * 获取启动器
	 * 
	 * @return
	 */
	public Map<String, JsonElement> getStarters() {

		Map<String, JsonElement> starterMap = Maps.newHashMap();

		manifestService.allManifest().forEach(json -> {
			Jsons starter = json.get("starter");
			String platformStr = starter.get("platform").asString();
			if (!Strings.isNullOrEmpty(platformStr)) {
				PlatformCategory category = EnumCollector.forClass(PlatformCategory.class).keyOf(platformStr);
				if (category != null) {
					if (starterMap.containsKey(platformStr)) {
						throw new IllegalArgumentException(
								"The Starter for " + platformStr + " conflict: \n" + starter.toPrettyJson());
					} else {
						starterMap.put(platformStr, starter.getRoot());
					}
				}
			}
		});

		return starterMap;
	}

	public Map<String, String> getPaths() {
		Map<String, String> pathsMap = Maps.newHashMap();

		manifestService.allManifest().forEach(json -> {
			Jsons paths = json.get("paths");
			if (paths.getRoot() != null) {
				paths.getRoot().getAsJsonObject().entrySet().forEach(entry -> {
					Jsons v = Jsons.from(entry.getValue());
					pathsMap.put(entry.getKey(), v.asString());
				});
			}
		});
		log.trace("发现Paths:\n" + Jsons.from(pathsMap).toPrettyJson());
		return pathsMap;
	}
}
