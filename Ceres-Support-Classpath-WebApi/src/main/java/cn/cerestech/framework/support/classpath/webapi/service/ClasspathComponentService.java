package cn.cerestech.framework.support.classpath.webapi.service;

import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.components.ComponentListener;
import cn.cerestech.framework.support.classpath.webapi.ClasspathUriProvider;

@Service
public class ClasspathComponentService implements ComponentListener {

	private static ConcurrentMap<String, ClasspathUriProvider> providerPools = Maps.newConcurrentMap();

	@Override
	public void recive(String beanName, Object bean) {
		if (bean instanceof ClasspathUriProvider) {
			ClasspathUriProvider provider = (ClasspathUriProvider) bean;
			String uri = provider.defineUri();
			providerPools.put(uri, provider);

		}
	}

	@Override
	public void onComplete() {

	}

	public Boolean exist(String uri) {
		return providerPools.containsKey(uri);
	}

	public String getContent(String uri) {
		String retStr = "";
		if (!Strings.isNullOrEmpty(uri)) {
			ClasspathUriProvider provider = providerPools.get(uri);
			if (provider != null) {
				retStr = Strings.nullToEmpty(provider.getContent());
			}
		}
		return retStr;
	}

}
