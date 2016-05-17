package cn.cerestech.support.web.console.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.date.Moment;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.core.strings.StringTypes;
import cn.cerestech.framework.core.utils.Encrypts;
import cn.cerestech.framework.core.utils.Random;
import cn.cerestech.framework.platform.service.PlatformService;
import cn.cerestech.support.web.console.dao.EmployeeDao;
import cn.cerestech.support.web.console.entity.Employee;
import cn.cerestech.support.web.console.errorcode.EmployeeErrorCodes;

@Service
public class EmployeeService {

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	PlatformService platformService;

	public Result<Employee> modifyPassword(Long eid, String pwd) {
		if (Strings.isNullOrEmpty(pwd)) {
			return Result.error(EmployeeErrorCodes.PASSWORD_CANNOT_EMPTY);
		}
		Employee e = employeeDao.findOne(eid);
		if (e != null) {
			e.setLoginPwd(Encrypts.md5(pwd));
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
		if (e != null && Strings.nullToEmpty(e.getLoginPwd()).equals(Encrypts.md5(oldPwd))) {
			e.setLoginPwd(Encrypts.md5(newPwd1));
			employeeDao.save(e);
			return Result.success().setObject(e);
		} else {
			return Result.error(EmployeeErrorCodes.LOGIN_FAILED);
		}
	}

	public Employee findById(Long id) {
		return employeeDao.findUniqueByPlatformIdAndId(platformService.getId(), id);
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
}
