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

	public Result<Employee> modifyPassword(Long eid, String pwd) {
		if (Strings.isNullOrEmpty(pwd)) {
			return Result.error(EmployeeErrorCodes.PASSWORD_CANNOT_EMPTY);
		}
		Employee e = employeeDao.findOne(eid);
		if (e != null) {
			// e.setLoginPwd(Encrypts.md5(pwd));
			employeeDao.save(e);
			return Result.success().setObject(e);
		} else {
			return Result.error().setMessage("");
		}
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

	// public List<Employee> search(Gender gender, YesNo frozen, String keyword,
	// Date dateRegisterFrom,
	// Date dateRegisterTo, Date dateLoginFrom, Date dateLoginTo) {
	// StringBuffer buffer = new StringBuffer(" 1=1");
	//
	// if (gender != null) {
	// buffer.append(" AND gender='" + gender.key() + "' ");
	// }
	// if (frozen != null) {
	// buffer.append(" AND frozen='" + frozen.key() + "'");
	// }
	//
	// if (dateRegisterFrom != null) {
	// buffer.append(" AND create_time >= timestamp('" +
	// FORMAT_DATE.format(dateRegisterFrom) + " 00:00:00') ");
	// }
	//
	// if (dateRegisterTo != null) {
	// buffer.append(" AND create_time <= timestamp('" +
	// FORMAT_DATE.format(dateRegisterTo) + " 23:59:59') ");
	// }
	//
	// if (dateLoginFrom != null) {
	// buffer.append(" AND last_login_time >= timestamp('" +
	// FORMAT_DATE.format(dateLoginFrom) + " 00:00:00') ");
	// }
	// if (dateLoginTo != null) {
	// buffer.append(" AND last_login_time <= timestamp('" +
	// FORMAT_DATE.format(dateLoginTo) + " 23:59:59') ");
	// }
	//
	// if (!Strings.isNullOrEmpty(keyword)) {
	// buffer.append(" AND (nickname LIKE '%" + keyword + "%' OR phone LIKE '%"
	// + keyword
	// + "%' OR position LIKE '%" + keyword + "%' OR work_num LIKE '%" + keyword
	// + "%')");
	// }
	//
	// buffer.append(" ORDER BY create_time DESC");
	// buffer.append(" LIMIT " + BaseEntity.MAX_QUERY_RECOREDS);
	//
	// List<Employee> retList = mysqlService.queryBy(Employee.class,
	// buffer.toString());
	// return retList;
	// }

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

		// 娇艳重复性

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
