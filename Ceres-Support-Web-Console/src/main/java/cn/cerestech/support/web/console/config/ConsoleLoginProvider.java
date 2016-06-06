package cn.cerestech.support.web.console.config;

import org.springframework.beans.factory.annotation.Autowired;

import cn.cerestech.framework.core.annotation.Provider;
import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.support.login.config.LoginProviderConfiguration;
import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.login.provider.LoginProvider;
import cn.cerestech.framework.support.login.provider.PasswordLoginProvider;
import cn.cerestech.framework.support.web.operator.RequestOperator;
import cn.cerestech.support.web.console.dao.EmployeeDao;
import cn.cerestech.support.web.console.entity.Employee;

@Provider(PlatformCategory.CONSOLE)
public class ConsoleLoginProvider extends LoginProviderConfiguration<Employee> implements RequestOperator {

	@Autowired
	EmployeeDao employeeDao;

	@Override
	public LoginProvider<Employee> getProvider() {
		return new PasswordLoginProvider<Employee>() {

			@Override
			public LoginDao<Employee> getDao() {
				return employeeDao;
			}

		};

	}

}
