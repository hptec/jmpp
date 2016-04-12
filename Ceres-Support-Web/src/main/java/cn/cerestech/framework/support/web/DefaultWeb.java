package cn.cerestech.framework.support.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.support.annotation.Manifest;
import cn.cerestech.framework.support.requirejs.service.DefaultWebService;

@RestController
@RequestMapping("api/web")
@Manifest("support/web/manifest.json")
public class DefaultWeb extends WebSupport {

	private Logger log = LogManager.getLogger();

	@Autowired
	DefaultWebService webService;

	@RequestMapping("systemconfigs.js")
	public void systemConfigs() {

		KV defMap = KV.on();
		defMap.put("jsModules", webService.getJsModules());
		defMap.put("starters", webService.getStarters());
		defMap.put("paths", webService.getPaths());

		zipOutRequireJson(defMap);
	}

}
