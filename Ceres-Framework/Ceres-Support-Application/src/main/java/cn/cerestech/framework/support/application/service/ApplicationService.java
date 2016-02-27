package cn.cerestech.framework.support.application.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import cn.cerestech.framework.core.Random;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.application.dao.ApplicationDao;
import cn.cerestech.framework.support.application.entity.Application;
import cn.cerestech.framework.support.application.errorcodes.ApplicationErrorCodes;

@Service
public class ApplicationService {

	public static Cache<String, String> tokenCache = null;

	private Logger log = LogManager.getLogger();

	@PostConstruct
	public void init() {
		if (tokenCache == null) {
			tokenCache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.HOURS).build();
		}
	}

	@Autowired
	ApplicationDao applicationDao;

	public Application getByDefault() {
		List<Application> appList = applicationDao.findAll();

		Application appToReturn = null;

		if (appList.isEmpty()) {
			appToReturn = createDefaultApplication();
			applicationDao.save(appToReturn);
		} else {
			appToReturn = appList.stream().findFirst().get();
		}

		return appToReturn;
	}

	private Application createDefaultApplication() {
		Application app = new Application();
		app.setAppId(Random.uuid());
		app.setAppSecret(Random.uuid());
		app.setDevelopCompanyName("成都塞瑞斯科技责任有限公司");
		app.setDevelopCompanyWebsite("http://www.cerestech.cn");
		app.setDevelopCompanyCopyright("Copyright@2014-2016");
		app.setAppFullName("塞瑞斯框架测试系统");
		app.setAppCopyright("Copyright@2014-2016");
		app.setAppShortName("框架测试系统");
		return app;
	}

	public Result<String> validateToken(String token) {

		if (tokenCache.asMap().containsKey(token)) {
			// 验证成功
			log.trace("validate token [" + token + "] success!");
			return Result.success(token);
		} else {
			log.trace("validate token [" + token + "] failed!");
			return Result.error(ApplicationErrorCodes.ACCESS_TOKEN_EXPIRED);
		}

	}

	public Result<String> authenticateAppIdSecrect(String appId, String appSecret) {
		Application app = applicationDao.findByAppId(appId);
		if (app == null) {
			log.trace("authenticate token [ " + appId + " , " + appSecret + " ] app null!");
			return Result.error(ApplicationErrorCodes.APPID_OR_APPSECRET_ERROR);
		}

		if (!Strings.nullToEmpty(appSecret).equals(app.getAppSecret())) {
			log.trace("authenticate token [ " + appId + " , " + appSecret + " ] id or secret error !");
			// 密码比对错误
			return Result.error(ApplicationErrorCodes.APPID_OR_APPSECRET_ERROR);
		}

		log.trace("authenticate token [ " + appId + " , " + appSecret + " ] success !");
		String accessToken = Random.uuid();
		tokenCache.put(accessToken, appId);
		return Result.success(accessToken);
	}
}
