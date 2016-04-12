package cn.cerestech.framework.support.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;

import cn.cerestech.framework.core.Core;
import cn.cerestech.framework.core.components.ComponentDispatcher;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.annotation.Manifest;

@Service
public class ManifestService implements ComponentDispatcher {

	private Logger log = LogManager.getLogger();

	private Map<String, Jsons> manifestPaths = Maps.newHashMap();

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
		return Lists.newArrayList(manifestPaths.values());
	}

	@Override
	public void onComplete() {
	}

}
