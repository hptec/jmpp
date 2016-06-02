package cn.cerestech.framework.support.login.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.platform.annotation.PlatformCategoryRequired;
import cn.cerestech.framework.support.login.config.LoginProviderConfiguration;
import cn.cerestech.framework.support.login.enums.ErrorCodes;
import cn.cerestech.framework.support.login.provider.LoginProvider;
import cn.cerestech.framework.support.login.provider.SmsLoginProvider;
import cn.cerestech.framework.support.login.service.LoginService;
import cn.cerestech.framework.support.webapi.WebApi;
import cn.cerestech.middleware.location.mobile.Mobile;

@RestController
@RequestMapping("/api/login")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LoginWebApi extends WebApi {

	@Autowired
	LoginService loginService;

	@RequestMapping("/doLogin")
	@PlatformCategoryRequired
	public void doLogin() {
		PlatformCategory category = getPlatformCategory();
		LoginProvider provider = LoginProviderConfiguration.getProvider(category);
		if (provider == null) {
			zipOut(Result.error(ErrorCodes.PROVIDER_NOT_SUPPORT));
		} else {
			Result ret = loginService.login(provider);
			zipOut(ret);
		}
	}

	@RequestMapping("/sms")
	@PlatformCategoryRequired
	public void sms(@RequestParam(name = "phone") String phone) {
		PlatformCategory category = getPlatformCategory();
		LoginProvider provider = LoginProviderConfiguration.getProvider(category);
		if (provider == null || !(provider instanceof SmsLoginProvider)) {
			zipOut(Result.error(ErrorCodes.PROVIDER_NOT_SUPPORT));
		} else {
			SmsLoginProvider p = (SmsLoginProvider) provider;
			Result ret = p.sendSmsCode(Mobile.fromChina(phone));
			zipOut(ret);
		}
	}

	@RequestMapping("/logout")
	@PlatformCategoryRequired
	public void logout() {
		PlatformCategory category = getPlatformCategory();
		LoginProvider provider = LoginProviderConfiguration.getProvider(category);
		if (provider == null) {
			zipOut(Result.error(ErrorCodes.PROVIDER_NOT_SUPPORT));
		} else {
			Result ret = loginService.logout();
			zipOut(ret);
		}
	}
}
