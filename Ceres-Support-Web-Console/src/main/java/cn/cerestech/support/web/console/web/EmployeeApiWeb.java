package cn.cerestech.support.web.console.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.login.operator.UserSessionOperator;
import cn.cerestech.support.web.console.dao.EmployeeDao;
import cn.cerestech.support.web.console.entity.Employee;
import cn.cerestech.support.web.console.service.EmployeeService;

@RequestMapping("/api/employee")
@RestController
public class EmployeeApiWeb extends AbstractConsoleWeb implements UserSessionOperator {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	EmployeeDao employeeDao;

	@RequestMapping("/get")
	public void get(@RequestParam("id") Long id) {
		Employee e = employeeDao.findOne(id);
		zipOut(Jsons.from(e.safty()));
	}

}
