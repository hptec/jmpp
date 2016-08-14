package cn.cerestech.framework.support.login.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.login.enums.LoginErrorCodes;
import cn.cerestech.framework.support.login.operator.UserSessionOperator;
import cn.cerestech.framework.support.login.provider.LoginProvider;
import cn.cerestech.framework.support.login.provider.SmsLoginProvider;
import cn.cerestech.framework.support.login.service.LoginService;
import cn.cerestech.framework.support.starter.annotation.PlatformRequired;
import cn.cerestech.framework.support.starter.web.WebSupport;
import cn.cerestech.middleware.location.mobile.Mobile;

@RestController
@RequestMapping("/api/login")
@SuppressWarnings({ "rawtypes" })
public class LoginWebApi extends WebSupport implements UserSessionOperator {

	@Autowired
	LoginService loginService;

	@Autowired
	LoginProvider loginProvider;

	@Autowired
	LoginDao loginDao;

	@RequestMapping("/doLogin")
	@PlatformRequired
	public void doLogin() {
		Result ret = loginService.login();
		zipOut(ret);
	}

	/**
	 * 校验用户的登录状态。如果返回的result.isSuccess=true则说明在登录中，反之为自动调用登录界面
	 */
	@RequestMapping("/validate")
	@PlatformRequired
	public void validate() {
		if (getUserId() == null) {
			Long id = loginService.getRememberId();
			String token = loginService.getRememberToken();
			if (id != null && !Strings.isNullOrEmpty(token)) {
				// 校验登录
				if (loginDao != null
						&& loginDao.findUniqueByIdAndLoginRememberTokenAndLoginRememberExpiredGreaterThan(id, token,
								new Date()) != null) {
					// 校验通过，记录入Session
					putUserId(id);
					zipOut(Result.success());
					return;
				}
			}
			zipOut(Result.error(LoginErrorCodes.LOGIN_REQUIRED));
		} else {
			zipOut(Result.success());
		}
	}

	@RequestMapping("/sms")
	@PlatformRequired
	public void sms(@RequestParam(name = "phone") String phone) {
		SmsLoginProvider p = (SmsLoginProvider) loginProvider;
		Result ret = p.sendSmsCode(Mobile.fromChina(phone));
		zipOut(ret);
	}

	@RequestMapping("/logout")
	@PlatformRequired
	public void logout() {
		Result ret = loginService.logout();
		zipOut(ret);
	}

	@RequestMapping("/definition")
	@PlatformRequired
	public void definition() {
		zipOut(loginProvider.getLoginFields());
	}

}
