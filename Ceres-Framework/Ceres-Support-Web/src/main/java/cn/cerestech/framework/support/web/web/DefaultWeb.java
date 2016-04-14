package cn.cerestech.framework.support.web.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.support.web.WebSupport;
import cn.cerestech.framework.support.web.annotation.Manifest;
import cn.cerestech.framework.support.web.service.ManifestService;

@RestController
@RequestMapping("api/web")
@Manifest("support/web/manifest.json")
public class DefaultWeb extends WebSupport {

	private Logger log = LogManager.getLogger();

	@Autowired
	ManifestService manifestService;

	@RequestMapping("systemconfigs.js")
	public void systemConfigs(@RequestParam("platform") String platformKey) {
		PlatformCategory category = EnumCollector.forClass(PlatformCategory.class).keyOf(platformKey);

		KV defMap = KV.on();
		defMap.put("jsModules", manifestService.getJsModules(category));
		defMap.put("starter", manifestService.getStarters(category));
		// defMap.put("paths", manifestService.getPaths());
		defMap.put("pages", manifestService.getPages(category));

		zipOutRequireJson(defMap);
	}

}
