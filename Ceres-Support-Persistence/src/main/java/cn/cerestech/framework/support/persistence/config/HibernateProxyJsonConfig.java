package cn.cerestech.framework.support.persistence.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import cn.cerestech.framework.core.json.Jsons;

@Component
public class HibernateProxyJsonConfig implements ApplicationRunner {

	Logger log = LogManager.getLogger();

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Jsons.registerAdapterFactory((HibernateProxyTypeAdapter.FACTORY));

	}
}
