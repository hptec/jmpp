package cn.cerestech.framework.support.requirejs.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.support.requirejs.service.RequireJsService;
import cn.cerestech.framework.support.web.RequireJsWebSupport;

@RestController
@RequestMapping("api/requirejs")


public class RequireJsWeb extends RequireJsWebSupport {

	private Logger log = LogManager.getLogger();

	@Autowired
	RequireJsService requireJsService;

	@RequestMapping("module_def.js")
	public void pathsjs() {

		KV defMap = KV.on();
		defMap.put("paths", requireJsService.getPaths());
		defMap.put("shim", requireJsService.getShim());
		defMap.put("requiredModule", requireJsService.getAngularRequiredModules());
		defMap.put("bootConfig", requireJsService.getBootConfig());

		zipOutRequireJson(defMap);
	}

	@RequestMapping("systemconfigs.js")
	public void systemConfigs() {

		KV defMap = KV.on();
		defMap.put("paths", requireJsService.getPaths());
		defMap.put("shim", requireJsService.getShim());
		defMap.put("requiredModule", requireJsService.getAngularRequiredModules());
		defMap.put("bootConfig", requireJsService.getBootConfig());

		zipOutRequireJson(defMap);
	}

}
