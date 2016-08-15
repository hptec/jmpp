package cn.cerestech.framework.support.starter.console.dao;

import cn.cerestech.framework.support.employee.dao.EmployeeDao;
import cn.cerestech.framework.support.employee.entity.Employee;
import cn.cerestech.framework.support.login.dao.LoginDao;

public interface EmployeeLoginDao extends LoginDao<Employee>, EmployeeDao {

}
