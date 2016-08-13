package cn.cerestech.framework.support.starter.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.gson.JsonElement;

import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.starter.dao.PlatformDao;
import cn.cerestech.framework.support.starter.enums.ModuleType;
import cn.cerestech.framework.support.starter.operator.PlatformOperator;

@Service
public class ManifestService implements PlatformOperator {

	private Logger log = LogManager.getLogger();

	@Autowired
	PlatformDao platformDao;

	// 数据缓存
	private List<Jsons> cacheManifests = Lists.newArrayList();
	private Map<PlatformCategory, Map<ModuleType, List<Jsons>>> cacheModules = Maps.newHashMap();

	private List<Jsons> getManifest() {
		cacheManifests.clear();
		log.trace("Manifest 未初始化，进行初始化..");
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		try {
			Resource[] resources = resourcePatternResolver.getResources("classpath*:**/manifest.json");
			for (Resource res : resources) {
				log.trace(res);
				String str = Resources.toString(res.getURL(), Charset.defaultCharset());
				cacheManifests.add(Jsons.from(str));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			log.trace("Manifest 初始化完成.");
		}
		return cacheManifests;

		// 每次重新扫描
		// if (cacheManifests.isEmpty()) {
		// log.trace("Manifest 未初始化，进行初始化..");
		// ResourcePatternResolver resourcePatternResolver = new
		// PathMatchingResourcePatternResolver();
		// try {
		// Resource[] resources =
		// resourcePatternResolver.getResources("classpath*:**/manifest.json");
		// for (Resource res : resources) {
		// log.trace(res);
		// String str = Resources.toString(res.getURL(),
		// Charset.defaultCharset());
		// cacheManifests.add(Jsons.from(str));
		// }
		// } catch (IOException e) {
		// throw new RuntimeException(e);
		// } finally {
		// log.trace("Manifest 初始化完成.");
		// }
		// }
		// return cacheManifests;
	}

	public List<Jsons> getModule(ModuleType type) {
		List<Jsons> ret = getCache(type);
		if (ret == null) {
			PlatformCategory category = platformDao.findOne(getPlatformId()).getCategory();
			List<Jsons> list = Lists.newArrayList();
			// 找到应用于所有的
			getManifest().forEach(manifest -> {
				if (log.isTraceEnabled()) {
					String key = manifest.get("name").asString();
					String desc = manifest.get("desc").asString();
					log.trace("开始扫描[" + type.desc() + "]：" + key + " - " + desc);
				}
				List<Jsons> cur = manifest.getRoot().getAsJsonObject().entrySet().stream().filter(entry -> {
					// 是否不带platform限定的
					log.trace("发现模块 " + entry.getKey() + " - " + type.key());
					return entry.getKey().startsWith(type.key());
				}).filter(entry -> {
					// 检查模块是否包含对应的平台规定关系
					if (entry.getKey().equals(type.key())) {
						// 没有PlatformCategory后缀则应用与全部
						return Boolean.TRUE;
					}

					// 拆分Platform Category
					if (category != null) {
						List<String> categorys = Splitter.on("_").omitEmptyStrings().trimResults()
								.splitToList(entry.getKey());
						return categorys.contains(category.key());
					} else {
						return Boolean.FALSE;
					}
				}).map(entry -> Jsons.from(entry.getValue())).flatMap(json -> json.asList().stream())
						.collect(Collectors.toList());
				list.addAll(cur);
				if (log.isTraceEnabled()) {
					log.trace("发现记录" + cur.size() + "条");
				}
			});
			putCache(type, list);
			ret = list;
		}
		return ret;
	}

	public List<JsonElement> getModuleElement(ModuleType type) {
		return getModule(type).stream().map(json -> json.getRoot()).collect(Collectors.toList());
	}

	private void putCache(ModuleType type, List<Jsons> json) {
		PlatformCategory category = platformDao.findOne(getPlatformId()).getCategory();
		Map<ModuleType, List<Jsons>> cateCache = cacheModules.get(category);
		if (cateCache == null) {
			cateCache = Maps.newHashMap();
			cacheModules.put(category, cateCache);
		}
		cateCache.put(type, json);
	}

	private List<Jsons> getCache(ModuleType type) {
		PlatformCategory category = platformDao.findOne(getPlatformId()).getCategory();
		Map<ModuleType, List<Jsons>> cateCache = cacheModules.get(category);
		if (cateCache != null && cateCache.containsKey(type)) {
			return cateCache.get(type);
		} else {
			return null;
		}
	}

}
