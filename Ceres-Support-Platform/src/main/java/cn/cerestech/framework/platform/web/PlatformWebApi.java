package cn.cerestech.framework.platform.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.platform.service.PlatformService;
import cn.cerestech.framework.support.web.RequireJsWebSupport;

@RestController
@RequestMapping("api/platform")
public class PlatformWebApi extends RequireJsWebSupport {

	@Autowired
	PlatformService platformService;

	@RequestMapping("query")
	public void query() {
		zipOutRequrieJson(platformService.getPlatform());
	}

}
