package cn.cerestech.framework.support.starter.console.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.starter.console.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long>, LoginDao<Employee> {

	Employee findUniqueByPlatformAndLoginIdIgnoreCase(String platformKey, String loginId);
}
