package cn.cerestech.framework.platform.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.platform.annotation.PlatformIgnore;
import cn.cerestech.framework.platform.entity.Platform;
import cn.cerestech.framework.platform.enums.ErrorCodes;
import cn.cerestech.framework.platform.service.PlatformService;
import cn.cerestech.framework.support.web.WebSupport;
import cn.cerestech.framework.support.web.annotation.Manifest;

@RestController
@RequestMapping("api/platform")
@Manifest("support/platform/manifest.json")
public class PlatformWebApi extends WebSupport {

	@Autowired
	PlatformService platformService;

	@RequestMapping("query")
	@PlatformIgnore
	public void query() {

		Platform pf = platformService.getPlatform();
		if (pf == null) {
			zipOut(Result.error(ErrorCodes.PLATFORM_AUTH_INCORRECT));
		} else {
			pf.setId(null);
			zipOut(Result.success(pf));
		}
	}

}
