package cn.cerestech.framework.support.login.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.platform.annotation.PlatformCategoryRequired;
import cn.cerestech.framework.support.login.config.LoginProviderConfiguration;
import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.login.enums.ErrorCodes;
import cn.cerestech.framework.support.login.operator.UserSessionOperator;
import cn.cerestech.framework.support.login.provider.LoginProvider;
import cn.cerestech.framework.support.login.provider.SmsLoginProvider;
import cn.cerestech.framework.support.login.service.LoginService;
import cn.cerestech.framework.support.web.web.WebApi;
import cn.cerestech.middleware.location.mobile.Mobile;

@RestController
@RequestMapping("/api/login")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LoginWebApi extends WebApi implements UserSessionOperator {

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

	/**
	 * 校验用户的登录状态。如果返回的result.isSuccess=true则说明在登录中，反之为自动调用登录界面
	 */
	@RequestMapping("/validate")
	@PlatformCategoryRequired
	public void validate() {
		if (getUserId() == null) {
			Long id = getRememberId();
			String token = getRememberToken();
			if (id != null && !Strings.isNullOrEmpty(token)) {
				// 校验登录
				LoginDao dao = loginProviders.getProvider(getPlatformCategory()).getDao();
				if (dao != null && dao.findUniqueByIdAndLoginRememberTokenAndLoginRememberExpiredGreaterThan(id, token,
						new Date()) != null) {
					// 校验通过，记录入Session
					putUserId(id);
					zipOut(Result.success());
					return;
				}
			}
			zipOut(Result.error(ErrorCodes.LOGIN_REQUIRED));
		} else {
			zipOut(Result.success());
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
