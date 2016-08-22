package cn.cerestech.framework.support.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beust.jcommander.internal.Lists;
import com.google.common.base.Strings;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.core.utils.Encrypts;
import cn.cerestech.framework.support.employee.dao.EmployeeDao;
import cn.cerestech.framework.support.employee.entity.Employee;
import cn.cerestech.framework.support.employee.enums.EmployeeErrorCodes;
import cn.cerestech.framework.support.login.entity.Login;
import cn.cerestech.framework.support.login.enums.LoginErrorCodes;
import cn.cerestech.framework.support.login.operator.UserSessionOperator;
import cn.cerestech.framework.support.starter.provider.PlatformProvider;

@Service
public class EmployeeService implements UserSessionOperator {

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	PlatformProvider platformProvider;

	/**
	 * 获取员工对象，只能获取当前Platform下属的员工
	 * 
	 * @param id
	 * @return
	 */
	public Employee get(Long id) {
		return employeeDao.findUniqueByPlatformAndDeleteTimeIsNullAndId(getPlatformKey(), id);
	}

	/**
	 * 根据当前登录用户获取最顶级的超级管理员
	 * 
	 * @return
	 */
	public Employee getTopEmployee() {
		Long ownerId = getUserId();
		if (ownerId == null) {
			return null;
		}
		Employee e = employeeDao.findOne(ownerId);
		return e.isAdmin() ? e : e.getParent();
	}

	/**
	 * 有管理员为其设置密码
	 * 
	 * @param eid
	 * @param newPwd1
	 * @param newPwd2
	 * @return
	 */
	public Result<Employee> modifyPassword(Long eid, String newPwd1, String newPwd2) {

		if (Strings.isNullOrEmpty(newPwd1) || Strings.isNullOrEmpty(newPwd2)) {
			return Result.error(EmployeeErrorCodes.PASSWORD_CANNOT_EMPTY);
		}
		if (!newPwd1.equals(newPwd2)) {
			return Result.error(EmployeeErrorCodes.PASSWORD_NOT_EQUAL);
		}
		Employee e = get(eid);
		e.getLogin().setPwd(Encrypts.md5(Encrypts.md5(newPwd1)));
		employeeDao.save(e);
		return Result.success().setObject(e);
	}

	public Result<Employee> modifyPassword(Long eid, String oldPwd, String newPwd1, String newPwd2) {
		if (Strings.isNullOrEmpty(oldPwd) || Strings.isNullOrEmpty(newPwd1) || Strings.isNullOrEmpty(newPwd2)) {
			return Result.error(EmployeeErrorCodes.PASSWORD_CANNOT_EMPTY);
		}
		if (!newPwd1.equals(newPwd2)) {
			return Result.error(EmployeeErrorCodes.PASSWORD_NOT_EQUAL);
		}
		Employee e = employeeDao.findOne(eid);
		if (e != null && Strings.nullToEmpty(e.getLogin().getPwd()).equals(Encrypts.md5(oldPwd))) {
			// e.setLoginPwd(Encrypts.md5(newPwd1));
			employeeDao.save(e);
			return Result.success().setObject(e);
		} else {
			// return Result.error(EmployeeErrorCodes.LOGIN_FAILED);
			return null;
		}
	}

	/**
	 * 添加子员工账号
	 * 
	 * @param employee
	 * @return
	 */
	public Result<Employee> updateSubEmployee(Employee employee) {
		if (employee == null) {
			return Result.error(EmployeeErrorCodes.EMPLOYEE_NOT_EXIST);
		}
		if (employee.getLogin() == null || Strings.isNullOrEmpty(employee.getLogin().getId())) {
			return Result.error(LoginErrorCodes.LOGIN_INFO_REQUIRED);
		}
		if (Strings.isNullOrEmpty(employee.getName())) {
			return Result.error(EmployeeErrorCodes.NAME_REQUIRED);
		}
		// loginId能在其他平台下存在
		int exist = employeeDao.countByPlatformAndDeleteTimeIsNullAndLoginIdAndParentIdNot(getPlatformKey(),
				employee.getLogin().getId(), getUserId());
		if (exist > 0) {
			return Result.error(EmployeeErrorCodes.LOGINID_CONFLICT);
		}

		employee.setParent(employeeDao.findOne(getUserId()));
		employee.setIsSuperAdmin(YesNo.NO);
		employee.setPlatform(getPlatformKey());

		if (employee.getId() == null) {
			// 新增模式
			employee.getLogin().setFrozen(YesNo.NO);
			employee.getLogin().setPwd(Encrypts.md5(Encrypts.md5("888888")));
			if (employee.getRoles() == null) {
				employee.setRoles(Lists.newArrayList());
			}
			if (!employee.getRoles().contains("SYS_ROLE_SALEMAN")) {
				employee.getRoles().add("SYS_ROLE_SALEMAN");
			}
		} else {
			Login loginInDb = employeeDao.findOne(employee.getId()).getLogin();
			loginInDb.setId(employee.getLogin().getId());// 登录名有可能被修改
			loginInDb.setFrozen(employee.getLogin().getFrozen());// 冻结状态可能被修改
			employee.setLogin(loginInDb);
		}

		// 校验重复性

		employeeDao.save(employee);
		return Result.success(employee);
	}

	public List<Employee> getSubEmployee() {
		return employeeDao.findByPlatformAndParentId(getPlatformKey(), getUserId());
	}

	private String getPlatformKey() {
		return platformProvider.get().getKey();
	}
}
