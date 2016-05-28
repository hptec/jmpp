package cn.cerestech.support.web.console.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.support.login.annotation.LoginRequired;
import cn.cerestech.support.web.console.entity.SysMenu;
import cn.cerestech.support.web.console.service.EmployeeService;
import cn.cerestech.support.web.console.service.MenuService;

@RequestMapping("/api/menu")
@RestController
public class MenuApiWeb extends AbstractConsoleWeb {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	MenuService menuService;

	@RequestMapping("/mine")
	@LoginRequired
	public void mine() {
		Long eid = getUserId();
		List<SysMenu> menus = menuService.getDefaultMenus();
		zipOut(menus);
	}

}
