package cn.cerestech.framework.platform.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.json.Jsons;
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
	public void query(@RequestParam("platform") String platform, @RequestParam("appid") String appid,
			@RequestParam("appsecret") String secret) {

		Platform pf = platformService.getPlatform(appid, secret);
		if (pf == null) {
			zipOut(Result.error(ErrorCodes.PLATFORM_AUTH_INCORRECT));
		} else {

			pf = Jsons.from(pf).to(Platform.class);
			pf.setPlatformSecret(null);
			pf.setId(null);
			zipOut(Result.success(pf));
		}

	}

}
