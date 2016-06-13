package cn.cerestech.framework.support.login.config;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import cn.cerestech.framework.core.annotation.Provider;
import cn.cerestech.framework.core.components.ComponentListener;
import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.core.operator.ProviderOperator;
import cn.cerestech.framework.support.login.entity.Loginable;
import cn.cerestech.framework.support.login.provider.LoginProvider;

@Component
public class LoginProviderConfiguration<T extends Loginable> implements ComponentListener, ProviderOperator {

	private Map<PlatformCategory, LoginProvider<T>> providerPool = Maps.newHashMap();

	protected Logger log = LogManager.getLogger();

	public LoginProvider<T> getProvider(PlatformCategory category) {
		return providerPool.get(category);
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public void recive(String beanName, Object bean) {
		if (bean != null && bean.getClass().isAnnotationPresent(Provider.class) && bean instanceof LoginProvider) {

			Provider providerAnno = bean.getClass().getAnnotation(Provider.class);

			PlatformCategory category = ((ProviderOperator) bean).forCategory();
			if (category == null) {
				log.warn("No PlatformCategory found!");
				return;
			}

			LoginProvider<T> provider = (LoginProvider<T>) bean;
			if (provider == null) {
				log.warn("No LoginProvider found!");
			}

			if (providerAnno.important()) {
				// 要求强制覆盖
				providerPool.put(category, provider);
			} else {
				// 判断是否冲突
				if (providerPool.containsKey(category)) {
					throw new IllegalArgumentException("LoginProvider Conflict:" + category);
				}
			}

		}

	}

	@Override
	public void onComplete() {

	}
}
