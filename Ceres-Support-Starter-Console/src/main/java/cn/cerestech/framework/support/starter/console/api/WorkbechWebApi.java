package cn.cerestech.framework.support.starter.console.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.utils.KV;
import cn.cerestech.framework.support.employee.service.EmployeeService;
import cn.cerestech.framework.support.login.annotation.LoginRequired;
import cn.cerestech.framework.support.login.operator.UserSessionOperator;
import cn.cerestech.framework.support.starter.console.entity.SysMenu;
import cn.cerestech.framework.support.starter.console.service.PrivilegeService;
import cn.cerestech.framework.support.starter.web.WebSupport;

@RestController
@RequestMapping("/api/console/workbench")
public class WorkbechWebApi extends WebSupport implements UserSessionOperator {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	PrivilegeService privilegeService;

	@RequestMapping("/config")
	@LoginRequired
	public void config() {
		KV retMap = KV.on();

		List<SysMenu> menus = privilegeService.getMenusByEmployee(getUserId());
		retMap.put("menus", menus);

		zipOut(retMap);
	}

}
