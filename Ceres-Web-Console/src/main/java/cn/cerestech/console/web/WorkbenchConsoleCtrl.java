package cn.cerestech.console.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.cerestech.console.annotation.LoginRequired;
import cn.cerestech.console.entity.Employee;
import cn.cerestech.console.entity.SysMenu;
import cn.cerestech.console.enums.ConsoleConfigKey;
import cn.cerestech.console.enums.LoginRedirectType;
import cn.cerestech.console.interceptor.ConsoleLoginIcptr;
import cn.cerestech.console.queryform.QueryForm;
import cn.cerestech.console.service.ConsoleConfigService;
import cn.cerestech.console.service.PrivilegeService;
import cn.cerestech.console.service.RequiredResourceService;
import cn.cerestech.framework.core.Core;
import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.localstorage.service.ResourceService;
import cn.cerestech.framework.web.enums.WebConfigKey;

@RequestMapping("$$ceres_sys/console")
@Controller
public class WorkbenchConsoleCtrl extends WebConsoleSupport {

	@Autowired
	ResourceService resourceService;

	@Autowired
	ConsoleConfigService configService;

	@Autowired
	PrivilegeService privilegeService;

	@RequestMapping("")
	@LoginRequired(redirectType = LoginRedirectType.URL)
	public @ResponseBody void index() {
		byte[] bytes = resourceService.queryClasspath("console/res/templates/workbench.html");

		KV param = KV.on();
		param.put("jslist", RequiredResourceService.getJs());
		param.put("csslist", RequiredResourceService.getCss());
		param.put("enums", enumMap());

		param.put("loginUrl", ConsoleLoginIcptr.LOGIN_URL);

		param.put("queryforms", Jsons.toJson(QueryForm.forms));

		param.put(ConsoleConfigKey.CONSOLE_DEVELOPER_NAME.key(),
				configService.query(ConsoleConfigKey.CONSOLE_DEVELOPER_NAME).stringValue());
		param.put(ConsoleConfigKey.CONSOLE_DEVELOPER_HREF.key(),
				configService.query(ConsoleConfigKey.CONSOLE_DEVELOPER_HREF).stringValue());
		param.put(ConsoleConfigKey.CONSOLE_DEVELOPER_COPYRIGHT_YEAR.key(),
				configService.query(ConsoleConfigKey.CONSOLE_DEVELOPER_COPYRIGHT_YEAR).stringValue());

		zipOut(freemarker(new String(bytes), param));
	}

	@RequestMapping("welcome/init")
	@LoginRequired
	public @ResponseBody void welcomeInit() {
		Map<String, Object> retMap = Maps.newHashMap();
		retMap.put(WebConfigKey.SITE_SHORT_NAME.name(),
				configService.query(WebConfigKey.SITE_SHORT_NAME).stringValue());
		retMap.put("employee", mysqlService.queryById(Employee.class, userId()));

		retMap.put("menus", privilegeService.menusOfEmployee(userId(), Boolean.FALSE));

		retMap.put("version", Core.version().toString());

		zipOut(Jsons.toJson(retMap));
	}

	@RequestMapping("controllers")
	@LoginRequired
	public @ResponseBody void menuControllers() {
		StringBuffer buf = new StringBuffer();
		List<SysMenu> allMenus = Lists.newArrayList();
		List<SysMenu> menus = privilegeService.menusOfEmployee(userId(), Boolean.TRUE);
		for (SysMenu m1 : menus) {
			allMenus.add(m1);
			if (m1.getSubmenu() != null && !m1.getSubmenu().isEmpty()) {
				for (SysMenu m2 : m1.getSubmenu()) {
					allMenus.add(m2);
					if (m2.getSubmenu() != null && !m2.getSubmenu().isEmpty()) {
						for (SysMenu m3 : m2.getSubmenu()) {
							allMenus.add(m3);
						}
					}
				}
			}
		}
		// 过滤掉link为空的菜单
		List<SysMenu> availableMenu = allMenus.stream().filter(m -> !Strings.isNullOrEmpty(m.getLink()))
				.collect(Collectors.toList());
		if (availableMenu != null && !availableMenu.isEmpty()) {
			buf.append("consoleApp.config(function($routeProvider, $locationProvider) {\n");
			buf.append("$routeProvider");
			for (SysMenu m : availableMenu) {
				// 连接有效才输出
				if (!Strings.isNullOrEmpty(m.getUrl_pattern())) {
					buf.append(".when('" + m.getUrl_pattern() + "', {");
				} else {
					buf.append(".when('" + m.getLink() + "', {");
				}
				buf.append("templateUrl : '" + m.getTemplateUrl() + "',");
				buf.append("controller : '" + m.getCtrl() + "'})");
			}
			buf.append(";});");
		}

		zipOut(buf.toString(), "text/javascript");
	}

	private Map<String, List<KV>> enumMap() {
		Map<String, List<KV>> enumMap = Maps.newHashMap();

		List<EnumCollector> enumList = EnumCollector.allEnums();

		enumList.stream().forEach(ec -> {
			log.trace(ec.getName());
			enumMap.put(ec.getName().replace('.', '_'), ec.toList());
		});

		return enumMap;
	}

}
