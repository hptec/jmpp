package cn.cerestech.framework.support.configuration.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.StringTypes;
import cn.cerestech.framework.platform.service.PlatformService;
import cn.cerestech.framework.support.configuration.dao.SysConfigDao;
import cn.cerestech.framework.support.configuration.entity.SysConfig;
import cn.cerestech.framework.support.configuration.enums.ConfigKey;

@Service
public class ConfigService {

	private Logger log = LogManager.getLogger();

	public static LoadingCache<ConfigKey, SysConfig> cachedKV = null;

	@Autowired
	SysConfigDao sysconfigDao;

	@Autowired
	PlatformService platformService;

	@PostConstruct
	public void init() {
		if (cachedKV == null) {
			cachedKV = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES)
					.build(new CacheLoader<ConfigKey, SysConfig>() {
						@Override
						public SysConfig load(ConfigKey key) throws Exception {
							Long platformId = platformService.getId();

							SysConfig config = sysconfigDao.findByPlatformIdAndKey(platformId, key.key());

							if (config == null) {
								// 如果不存在则在数据库插入一条
								config = new SysConfig();
								config.setDesc(key.desc());
								config.setKey(key.key());
								config.setValue(key.defaultValue());
								sysconfigDao.save(config);
							}
							return config;
						}
					});
		}
	}

	public StringTypes query(ConfigKey key) {
		String value = key.defaultValue();
		try {
			SysConfig config = cachedKV.get(key);
			value = Strings.nullToEmpty(config.getValue());
		} catch (ExecutionException e) {
			log.catching(e);
		}

		return new StringTypes(value);
	}

	public Map<ConfigKey, StringTypes> querySet(ConfigKey... key) {
		Map<ConfigKey, StringTypes> retMap = Maps.newHashMap();
		for (ConfigKey k : key) {
			retMap.put(k, query(k));
		}
		return retMap;
	}

	public Map<ConfigKey, StringTypes> querySet(Set<ConfigKey> keys) {
		Map<ConfigKey, StringTypes> retMap = Maps.newHashMap();
		for (ConfigKey k : keys) {
			retMap.put(k, query(k));
		}
		return retMap;
	}

	public void update(ConfigKey key, String value) {
		SysConfig config = null;
		try {
			config = cachedKV.get(key);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
		config.setPlatformId(platformService.getId());
		config.setValue(value);
		config.setDesc(key.desc());
		sysconfigDao.saveAndFlush(config);
		cachedKV.refresh(key);// 刷新key值
	}

	public void update(Map<ConfigKey, String> param) {
		param.forEach((k, v) -> update(k, v));
	}

	public List<SysConfig> all() {
		return sysconfigDao.findAll();
	}

}
