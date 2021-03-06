package cn.cerestech.framework.support.starter.console.provider;

import org.springframework.beans.factory.annotation.Autowired;

import cn.cerestech.framework.support.employee.dao.EmployeeDao;
import cn.cerestech.framework.support.employee.entity.Employee;
import cn.cerestech.framework.support.login.entity.Login;
import cn.cerestech.framework.support.login.provider.PasswordLoginProvider;
import cn.cerestech.framework.support.starter.provider.PlatformProvider;

public abstract class ConsoleLoginProvider extends PasswordLoginProvider<Employee> {

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	PlatformProvider platformProvider;

	@Override
	public Employee getLoginFromDB(Login fromLogin) {
		Employee e = employeeDao.findUniqueByPlatformAndDeleteTimeIsNullAndLoginIdIgnoreCase(
				platformProvider.get().getKey(), fromLogin.getId());
		return e;
	}
}
