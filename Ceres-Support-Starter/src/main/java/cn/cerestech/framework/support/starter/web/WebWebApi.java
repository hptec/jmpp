package cn.cerestech.framework.support.starter.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.utils.KV;
import cn.cerestech.framework.support.starter.enums.ModuleType;
import cn.cerestech.framework.support.starter.provider.MainPageProvider;
import cn.cerestech.framework.support.starter.service.ManifestService;

@RestController
@RequestMapping("api/web")
public class WebWebApi extends WebSupport {

	private Logger log = LogManager.getLogger();

	@Autowired
	ManifestService manifestService;
	@Autowired
	MainPageProvider mainPageProvider;

	@RequestMapping("systemconfigs.js")
	public void systemConfigs() {

		KV defMap = KV.on();
		defMap.put("jsModules", manifestService.getModuleElement(ModuleType.JAVASCRIPT));
		defMap.put("starter", manifestService.getModuleElement(ModuleType.STARTER));
		// defMap.put("paths", manifestService.getPaths());
		defMap.put("pages", manifestService.getModuleElement(ModuleType.PAGES));
		defMap.put("html5mode", mainPageProvider.isHtml5Mode());

		zipOutRequireJson(defMap);
	}

	/**
	 * 输出系统中配置的所有DescribableEnum枚举
	 */
	@RequestMapping("enums")
	public void enums() {
		KV retMap = KV.on();
		EnumCollector.allEnums().stream().forEach(ec -> {
			retMap.put(ec.getName().replace('.', '_'), ec.toList());
		});
		zipOut(retMap);
	}

}
