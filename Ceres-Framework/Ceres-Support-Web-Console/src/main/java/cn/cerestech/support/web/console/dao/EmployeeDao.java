package cn.cerestech.support.web.console.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.support.web.console.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

	Employee findUniqueByLoginId(String loginId);

	Employee findUniqueByIdAndRememberTokenAndRememberExpiredGreaterThan(Long id, String remmeberToken,
			Date rememberExpired);
}
