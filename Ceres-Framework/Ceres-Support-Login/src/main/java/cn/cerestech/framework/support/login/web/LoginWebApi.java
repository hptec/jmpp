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
import cn.cerestech.framework.support.web.web.WebApi;
import cn.cerestech.middleware.location.mobile.Mobile;

@RestController
@RequestMapping("/api/login")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LoginWebApi extends WebApi {

	@Autowired
	LoginService loginService;

	@Autowired
	LoginProviderConfiguration loginProviders;

	@RequestMapping("/doLogin")
	@PlatformCategoryRequired
	public void doLogin() {
		LoginProvider provider = getProvider();
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
		LoginProvider provider = getProvider();
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
		LoginProvider provider = getProvider();
		if (provider == null) {
			zipOut(Result.error(ErrorCodes.PROVIDER_NOT_SUPPORT));
		} else {
			Result ret = loginService.logout();
			zipOut(ret);
		}
	}

	@RequestMapping("/definition")
	@PlatformCategoryRequired
	public void definition() {
		LoginProvider provider = getProvider();
		if (provider == null) {
			zipOut(Result.error(ErrorCodes.PROVIDER_NOT_SUPPORT));
		} else {
			zipOut(provider.getLoginFields());
		}
	}

	private LoginProvider getProvider() {
		PlatformCategory category = getPlatformCategory();
		if (category == null) {
			return null;
		}
		return loginProviders.getProvider(category);
	}
}
