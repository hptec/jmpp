package cn.cerestech.support.web.console.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.support.web.console.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long>, LoginDao<Employee> {

}
