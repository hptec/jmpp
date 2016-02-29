package cn.cerestech.console.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import cn.cerestech.console.entity.Employee;
import cn.cerestech.console.entity.EmployeeActionLog;
import cn.cerestech.console.enums.Console;
import cn.cerestech.console.errorcodes.EmployeeErrorCodes;
import cn.cerestech.framework.core.Encrypts;
import cn.cerestech.framework.core.Random;
import cn.cerestech.framework.core.StringTypes;
import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.core.enums.Gender;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.service.BaseService;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.persistence.entity.BaseEntity;

@Service
public class EmployeeService extends BaseService {

	@Autowired
	ConsoleConfigService configService;

	public Result<Employee> login(String usr, String pwd, Boolean isRemember) {
		if (Strings.isNullOrEmpty(usr) || Strings.isNullOrEmpty(pwd)) {
			return Result.error(EmployeeErrorCodes.LOGIN_FAILED);
		}

		Employee u = mysqlService.queryUnique(Employee.class, " usr = '" + usr + "'");

		if (u == null) {
			return Result.error(EmployeeErrorCodes.LOGIN_FAILED);
		}

		if (Strings.nullToEmpty(u.getPwd()).equals(Encrypts.md5(pwd))) {
			// 比对用户名密码
			if (new StringTypes(u.getFrozen()).boolValue()) {
				actionLog(u.getId(), Console.Employee.ActionType.LOGIN_FROZEN, Console.SysObj.LOGIN, "");
				return Result.error(EmployeeErrorCodes.EMPLOYEE_FROZEN);
			} else {
				// 登录成功
				actionLog(u.getId(), Console.Employee.ActionType.LOGIN_SUCCESS, Console.SysObj.LOGIN, "");
			}
		} else {
			actionLog(u.getId(), Console.Employee.ActionType.LOGIN_PWD_ERROR, Console.SysObj.LOGIN, "");
			return Result.error(EmployeeErrorCodes.LOGIN_FAILED);
		}

		// 记录remember me
		Date now = new Date();
		if (isRemember) {
			// 记住登录
			u.setRemember_token(Random.uuid());
			u.setRemember_expired(DateUtils.addMonths(now, 3));
		} else {
			// 清除登录
			u.setRemember_token(null);
			u.setRemember_expired(now);
		}
		u.setLast_login_time(now);

		mysqlService.update(u);

		// user log 记录登录地址、ip
		return Result.success().setObject(u).setMessage("登录成功");
	}

	public void actionLog(Long employeeId, DescribableEnum action, DescribableEnum target, String content) {
		if (configService.isEmployeeActionLogEnable()) {
			Employee e = mysqlService.queryById(Employee.class, employeeId);
			if (e != null) {
				EmployeeActionLog log = new EmployeeActionLog();
				log.setEmployee_id(employeeId);
				log.setEmployee_name(e.getNickname());
				log.setCreate_time(new Date());

				if (action != null) {
					log.setAction_key(action.key());
					log.setAction_desc(action.desc());
				}

				if (target != null) {
					log.setTarget_key(target.key());
					log.setTarget_desc(target.desc());
				}
				log.setTarget_value(content);
				mysqlService.insert(log);
			}

		}

	}

	public Result<Employee> modifyPassword(Long eid, String pwd) {
		if (Strings.isNullOrEmpty(pwd)) {
			return Result.error(EmployeeErrorCodes.PASSWORD_CANNOT_EMPTY);
		}
		Employee e = mysqlService.queryById(Employee.class, eid);
		if (e != null) {
			e.setPwd(Encrypts.md5(pwd));
			mysqlService.update(e);
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
		Employee e = mysqlService.queryById(Employee.class, eid);
		if (e != null && Strings.nullToEmpty(e.getPwd()).equals(Encrypts.md5(oldPwd))) {
			e.setPwd(Encrypts.md5(newPwd1));
			mysqlService.update(e);
			return Result.success().setObject(e);
		} else {
			return Result.error(EmployeeErrorCodes.LOGIN_FAILED);
		}
	}

	public List<Employee> search(Gender gender, YesNo frozen, String keyword, Date dateRegisterFrom,
			Date dateRegisterTo, Date dateLoginFrom, Date dateLoginTo) {
		StringBuffer buffer = new StringBuffer(" 1=1");

		if (gender != null) {
			buffer.append(" AND gender='" + gender.key() + "' ");
		}
		if (frozen != null) {
			buffer.append(" AND frozen='" + frozen.key() + "'");
		}

		if (dateRegisterFrom != null) {
			buffer.append(" AND create_time >= timestamp('" + FORMAT_DATE.format(dateRegisterFrom) + " 00:00:00') ");
		}

		if (dateRegisterTo != null) {
			buffer.append(" AND create_time <= timestamp('" + FORMAT_DATE.format(dateRegisterTo) + " 23:59:59') ");
		}

		if (dateLoginFrom != null) {
			buffer.append(" AND last_login_time >= timestamp('" + FORMAT_DATE.format(dateLoginFrom) + " 00:00:00') ");
		}
		if (dateLoginTo != null) {
			buffer.append(" AND last_login_time <= timestamp('" + FORMAT_DATE.format(dateLoginTo) + " 23:59:59') ");
		}

		if (!Strings.isNullOrEmpty(keyword)) {
			buffer.append(" AND (nickname LIKE '%" + keyword + "%' OR phone LIKE '%" + keyword
					+ "%' OR position LIKE '%" + keyword + "%' OR work_num LIKE '%" + keyword + "%')");
		}

		buffer.append(" ORDER BY create_time DESC");
		buffer.append(" LIMIT " + BaseEntity.MAX_QUERY_RECOREDS);

		List<Employee> retList = mysqlService.queryBy(Employee.class, buffer.toString());
		return retList;
	}
}
