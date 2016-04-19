package cn.cerestech.support.web.console.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.annotation.LoginRequired;
import cn.cerestech.support.web.console.entity.Employee;
import cn.cerestech.support.web.console.service.EmployeeService;

@RequestMapping("/api/menu")
@RestController
public class MenuApiWeb extends AbstractConsoleWeb {

	@Autowired
	EmployeeService employeeService;

	@RequestMapping("/mine")
	@LoginRequired
	public void mine() {
		Long eid = getUserId();
		System.out.println(eid);
	}

}
