package cn.cerestech.support.web.console.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.web.annotation.PlatformDefaultPage;
import cn.cerestech.support.classpath.ClasspathService;
import cn.cerestech.support.web.console.entity.SysMenu;
import cn.cerestech.support.web.console.service.MenuService;

@RestController
@RequestMapping("$$ceres_sys/console")
public class DefaultWeb extends AbstractConsoleWeb {
	private Logger log = LogManager.getLogger();

	@Autowired
	ClasspathService classpathService;

	@Autowired
	MenuService menuService;

	@RequestMapping("")
	@PlatformDefaultPage(PlatformCategory.CONSOLE)
	public byte[] index() {
		return themeResource("index.html");
	}

	@RequestMapping("test")
	public byte[] test() {
		List<SysMenu> menus = menuService.getMyMenus();
		Jsons json = Jsons.from(menus);
		String str = json.prettyPrint().toJson();
		return ("<pre>" + str + "</pre>").getBytes();

		// menuService.doSynchronizedIfNecessary();
		// return "".getBytes();
	}

}
