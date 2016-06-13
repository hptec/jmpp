package cn.cerestech.support.web.console.provider;

import org.springframework.beans.factory.annotation.Autowired;

import cn.cerestech.framework.core.annotation.Provider;
import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.login.provider.PasswordLoginProvider;
import cn.cerestech.support.web.console.dao.EmployeeDao;
import cn.cerestech.support.web.console.entity.Employee;

@Provider(PlatformCategory.CONSOLE)
public class ConsoleLoginProvider extends PasswordLoginProvider<Employee> {

	@Autowired
	EmployeeDao employeeDao;

	@Override
	public LoginDao<Employee> getDao() {
		return employeeDao;
	}

}
