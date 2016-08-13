package cn.cerestech.framework.support.starter.console.provider;

import org.springframework.beans.factory.annotation.Autowired;

import cn.cerestech.framework.support.login.entity.Login;
import cn.cerestech.framework.support.login.provider.PasswordLoginProvider;
import cn.cerestech.framework.support.starter.console.dao.EmployeeDao;
import cn.cerestech.framework.support.starter.console.entity.Employee;
import cn.cerestech.framework.support.starter.operator.PlatformOperator;

public abstract class ConsoleLoginProvider extends PasswordLoginProvider<Employee> implements PlatformOperator {

	@Autowired
	EmployeeDao employeeDao;

	@Override
	public Employee getLoginFromDB(Login fromLogin) {
		Employee e = employeeDao.findUniqueByPlatformAndLoginIdIgnoreCase(getPlatformKey(), fromLogin.getId());
		return e;
	}
}
