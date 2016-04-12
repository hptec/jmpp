package cn.cerestech.support.web.console.web;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.platform.annotation.PlatformIgnore;
import cn.cerestech.support.web.console.entity.SysMenu;
import cn.cerestech.support.web.console.entity.SysMenuPage;
import cn.cerestech.support.web.console.service.MenuService;

@RestController
@RequestMapping("/api/console")
public class DefaultApiWeb extends AbstractConsoleWeb {
	private Logger log = LogManager.getLogger();

	@Autowired
	MenuService menuService;

	/**
	 * 读取系统中配置的菜单中的页面用于加载路由
	 */
	@RequestMapping("pages")
	@PlatformIgnore
	public void pages() {
		List<SysMenuPage> pages = menuService.getFlatDefaultPages().values().stream().collect(Collectors.toList());
		zipOutRequireJson(pages);
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

	@RequestMapping("/menu.js")
	public void menu() {

		zipOutRequireJson(menuService.getDefaultMenus());
	}

}
