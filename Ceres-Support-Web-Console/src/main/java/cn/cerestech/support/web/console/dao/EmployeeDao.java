package cn.cerestech.support.web.console.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.support.web.console.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

	Employee findUniqueByPlatformIdAndLoginId(Long platformId, String loginId);

	Employee findUniqueByPlatformIdAndId(Long platformId, Long id);
}
