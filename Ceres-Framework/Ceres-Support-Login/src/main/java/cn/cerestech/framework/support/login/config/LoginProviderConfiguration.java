package cn.cerestech.framework.support.login.config;

import java.util.Map;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import com.google.common.collect.Maps;

import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.core.operator.ProviderOperator;
import cn.cerestech.framework.support.login.entity.Loginable;
import cn.cerestech.framework.support.login.provider.LoginProvider;

public abstract class LoginProviderConfiguration<T extends Loginable> implements ApplicationRunner, ProviderOperator {

	private static Map<PlatformCategory, LoginProvider> providerPool = Maps.newHashMap();

	protected Logger log = LogManager.getLogger();

	@Override
	public void run(ApplicationArguments args) throws Exception {

		PlatformCategory category = forCategory();
		if (category == null) {
			log.warn("No PlatformCategory found!");
			return;
		}

		LoginProvider<T> provider = getProvider();
		if (provider == null) {
			log.warn("No LoginProvider found!");
			return;
		}

		providerPool.put(category, provider);

	}

	public abstract LoginProvider<T> getProvider();

	public static LoginProvider getProvider(PlatformCategory category) {
		return providerPool.get(category);
	}
}
