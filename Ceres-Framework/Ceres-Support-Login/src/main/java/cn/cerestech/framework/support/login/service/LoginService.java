package cn.cerestech.framework.support.login.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import cn.cerestech.framework.core.components.ComponentDispatcher;
import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.support.login.annotation.LoginProvider;
import cn.cerestech.framework.support.login.provider.LoginServiceProvider;

@Service
public class LoginService implements ComponentDispatcher {

	private static Map<PlatformCategory, LoginServiceProvider> providerPool = Maps.newHashMap();

	public LoginServiceProvider getServiceProvider(PlatformCategory platform) {
		return providerPool.get(platform);
	}

	@Override
	public void recive(String beanName, Object bean) {
		if (bean instanceof LoginServiceProvider && bean.getClass().isAnnotationPresent(LoginProvider.class)) {
			LoginServiceProvider provider = (LoginServiceProvider) bean;
			LoginProvider providerAnno = bean.getClass().getAnnotation(LoginProvider.class);
			if (providerPool.containsKey(providerAnno.value())) {
				throw new IllegalArgumentException("Login Provider conflict: [" + providerAnno.value().name() + "] "
						+ bean.getClass().getCanonicalName());

			} else {
				providerPool.put(providerAnno.value(), provider);
			}
		}

	}

	@Override
	public void onComplete() {

	}
}
