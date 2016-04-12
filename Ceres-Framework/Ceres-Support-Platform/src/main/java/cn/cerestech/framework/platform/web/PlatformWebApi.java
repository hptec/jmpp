package cn.cerestech.framework.platform.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.platform.service.PlatformService;
import cn.cerestech.framework.support.web.WebSupport;

@RestController
@RequestMapping("api/platform")
public class PlatformWebApi extends WebSupport {

	@Autowired
	PlatformService platformService;

	@RequestMapping("query")
	public void query() {
		zipOutRequireJson(platformService.getPlatform());
	}

}
