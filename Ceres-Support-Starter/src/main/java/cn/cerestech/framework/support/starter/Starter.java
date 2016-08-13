package cn.cerestech.framework.support.starter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.support.starter.dao.PlatformDao;
import cn.cerestech.framework.support.starter.entity.CustomerService;
import cn.cerestech.framework.support.starter.entity.Developer;
import cn.cerestech.framework.support.starter.entity.Frontend;
import cn.cerestech.framework.support.starter.entity.Platform;
import cn.cerestech.framework.support.starter.provider.MainPageProvider;
import cn.cerestech.framework.support.starter.provider.PlatformProvider;

@ComponentScan(basePackages = "cn.cerestech")
@EnableJpaRepositories("cn.cerestech")
@EntityScan("cn.cerestech")
@EnableAutoConfiguration
@SpringBootApplication
@Component
@Configuration
public abstract class Starter implements ApplicationRunner {

	@Autowired
	protected PlatformDao platformDao;

	@Autowired
	protected PlatformProvider platformProvider;

	protected Logger log = LogManager.getLogger();

	@Bean
	@ConditionalOnMissingClass
	public PlatformProvider getPlatformProvider() {
		return new PlatformProvider() {

			@Override
			public Platform get() {
				Platform p = new Platform();
				p.setKey("CERES_DEFAULT_PLATFORM");
				p.setCategory(PlatformCategory.NULL);

				CustomerService cs = new CustomerService();
				p.setCustomerService(cs);

				Developer developer = new Developer();
				developer.setCopyright("版权所有 2014 - 2016 ");
				developer.setName("成都塞瑞斯科技有限公司");
				developer.setWebsite("http://www.cerestech.cn");
				p.setDeveloper(developer);

				Frontend frontend = new Frontend();
				frontend.setFullName("塞瑞斯科技");
				frontend.setShortName("C");
				p.setFrontend(frontend);

				return p;
			}

		};
	}

	@Bean
	@ConditionalOnMissingBean(MainPageProvider.class)
	public MainPageProvider getMainPageProvider() {
		return new MainPageProvider() {
			@Override
			public String get() {
				return "";
			}
		};
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 执行Platform 同步
		Platform p = platformProvider.get();
		if (p == null) {
			throw new RuntimeException("Platform 没有指定");
		}
		Platform pInDb = platformDao.findUniqueByKey(p.getKey());
		if (pInDb == null) {
			// 注入数据
			platformDao.save(p);
		}
	}

}
