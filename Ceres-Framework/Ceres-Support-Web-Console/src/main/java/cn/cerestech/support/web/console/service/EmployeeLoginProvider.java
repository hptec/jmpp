package cn.cerestech.support.web.console.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.date.Moment;
import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.core.strings.StringTypes;
import cn.cerestech.framework.core.utils.Encrypts;
import cn.cerestech.framework.core.utils.Random;
import cn.cerestech.framework.platform.service.PlatformService;
import cn.cerestech.framework.support.login.annotation.LoginProvider;
import cn.cerestech.framework.support.login.interceptor.LoginInterceptor;
import cn.cerestech.framework.support.login.provider.LoginServiceProvider;
import cn.cerestech.support.web.console.dao.EmployeeDao;
import cn.cerestech.support.web.console.entity.Employee;
import cn.cerestech.support.web.console.errorcode.EmployeeErrorCodes;

@LoginProvider(PlatformCategory.CONSOLE)
@Service
public class EmployeeLoginProvider implements LoginServiceProvider<Employee> {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	PlatformService platformService;

	@Autowired
	LoginInterceptor loginInterceptor;

	@Override
	public Result<Employee> login(Employee entity, Boolean isRemember) {

		if (entity == null) {
			return Result.error(EmployeeErrorCodes.LOGIN_FAILED);
		}

		if (Strings.isNullOrEmpty(entity.getLoginId()) || Strings.isNullOrEmpty(entity.getLoginPwd())) {
			return Result.error(EmployeeErrorCodes.LOGIN_FAILED);
		}

		Employee u = employeeDao.findUniqueByPlatformIdAndLoginId(platformService.getId(), entity.getLoginId());

		if (u == null) {
			return Result.error(EmployeeErrorCodes.LOGIN_FAILED);
		}

		if (Strings.nullToEmpty(u.getLoginPwd()).equals(Encrypts.md5(entity.getLoginPwd()))) {
			// 比对用户名密码
			if (new StringTypes(u.getFrozen()).boolValue()) {
				// actionLog(u.getId(),
				// Console.Employee.ActionType.LOGIN_FROZEN,
				// Console.SysObj.LOGIN, "");
				return Result.error(EmployeeErrorCodes.EMPLOYEE_FROZEN);
			} else {
				// 登录成功
				// actionLog(u.getId(),
				// Console.Employee.ActionType.LOGIN_SUCCESS,
				// Console.SysObj.LOGIN, "");
			}
		} else {
			// actionLog(u.getId(), Console.Employee.ActionType.LOGIN_PWD_ERROR,
			// Console.SysObj.LOGIN, "");
			return Result.error(EmployeeErrorCodes.LOGIN_FAILED);
		}

		// 记录remember me
		Date now = new Date();
		if (isRemember) {
			// 记住登录
			u.setRememberToken(Random.uuid());
			u.setRememberExpired(Moment.now().addMonth(3).toDate());
		} else {
			// 清除登录
			u.setRememberToken(null);
			u.setRememberExpired(now);
		}
		u.setLastLoginTime(now);

		employeeDao.save(u);

		// user log 记录登录地址、ip
		return Result.success().setObject(u).setMessage("登录成功");
	}

	@Override
	public Boolean validateRememberToken(String token, Long id) {
		Employee e = employeeDao.findUniqueByPlatformIdAndIdAndRememberTokenAndRememberExpiredGreaterThan(
				platformService.getId(), id, token, new Date());
		return e != null;
	}

}
