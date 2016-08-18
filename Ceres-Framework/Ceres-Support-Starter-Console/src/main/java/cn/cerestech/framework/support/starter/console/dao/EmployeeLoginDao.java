package cn.cerestech.framework.support.starter.console.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.framework.support.employee.entity.Employee;
import cn.cerestech.framework.support.login.dao.LoginDao;

public interface EmployeeLoginDao extends LoginDao<Employee>, JpaRepository<Employee, Long> {

}
