package cn.cerestech.framework.support.starter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.starter.entity.Platform;
import cn.cerestech.framework.support.starter.enums.ErrorCodes;
import cn.cerestech.framework.support.starter.provider.PlatformProvider;

@RestController
@RequestMapping("api/platform")
public class PlatformWebApi extends WebSupport {

	@Autowired
	PlatformProvider platformProvider;

	@RequestMapping("query")
	public void query() {
		Platform pf = platformProvider.get();
		if (pf == null) {
			zipOut(Result.error(ErrorCodes.PLATFORM_AUTH_INCORRECT));
		} else {
			pf.setId(null);
			zipOut(Result.success(pf));
		}
	}

	@RequestMapping("get")
	public void get() {
		Platform pf = platformProvider.get();
		if (pf == null) {
			zipOutRequireJson(Result.error(ErrorCodes.PLATFORM_AUTH_INCORRECT));
		} else {
			pf.setId(null);
			zipOutRequireJson(pf);
		}
	}

}
