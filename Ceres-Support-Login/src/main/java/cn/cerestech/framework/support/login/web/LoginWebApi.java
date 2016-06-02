package cn.cerestech.framework.support.login.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.platform.annotation.PlatformCategoryRequired;
import cn.cerestech.framework.support.login.config.LoginProviderConfiguration;
import cn.cerestech.framework.support.login.provider.LoginProvider;
import cn.cerestech.framework.support.login.service.LoginService;
import cn.cerestech.framework.support.webapi.WebApi;

@RestController
@RequestMapping("/api/login")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LoginWebApi extends WebApi {

	@Autowired
	LoginService loginService;

	@RequestMapping("/doLogin")
	@PlatformCategoryRequired
	public void doLogin(@RequestParam(name = "remember", defaultValue = "false") Boolean remember) {
		PlatformCategory category = getPlatformCategory();
		LoginProvider provider = LoginProviderConfiguration.getProvider(category);
		Result ret = loginService.login(provider, remember);
		zipOut(ret);
	}
}
