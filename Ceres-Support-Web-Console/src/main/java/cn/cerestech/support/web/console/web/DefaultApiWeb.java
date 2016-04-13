package cn.cerestech.support.web.console.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.platform.entity.Platform;
import cn.cerestech.framework.platform.service.PlatformService;
import cn.cerestech.support.web.console.entity.SysMenu;
import cn.cerestech.support.web.console.service.MenuService;

@RestController
@RequestMapping("/api/console")
public class DefaultApiWeb extends AbstractConsoleWeb {
	private Logger log = LogManager.getLogger();

	@Autowired
	MenuService menuService;

	@Autowired
	PlatformService platformService;

	@RequestMapping("test")
	public byte[] test() {
		List<SysMenu> menus = menuService.getMyMenus();
		Jsons json = Jsons.from(menus);
		String str = json.prettyPrint().toJson();
		return ("<pre>" + str + "</pre>").getBytes();

		// menuService.doSynchronizedIfNecessary();
		// return "".getBytes();
	}

	@RequestMapping("/appconfig.js")
	public void appConfig() {

		KV map = KV.on();

		Platform platform = platformService.getPlatform();
		map.put("platform", platform);

		zipOutRequireJson(map);
	}

}
