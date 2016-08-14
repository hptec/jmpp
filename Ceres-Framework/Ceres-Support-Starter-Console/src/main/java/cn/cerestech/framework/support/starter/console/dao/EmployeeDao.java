package cn.cerestech.framework.support.starter.console.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.starter.console.entity.Employee;

public interface EmployeeDao
		extends JpaRepository<Employee, Long>, LoginDao<Employee>, JpaSpecificationExecutor<Employee> {

	/**
	 * 未被删除的同平台下的账号
	 * @param platformKey
	 * @param loginId
	 * @return
	 */
	Employee findUniqueByPlatformAndDeleteTimeIsNullAndLoginIdIgnoreCase(String platformKey, String loginId);

	Employee findUniqueByPlatformAndDeleteTimeIsNullAndId(String platformKey, Long id);

	/**
	 * 同平台下非自己的账号不能有重复登录名
	 * 
	 * @param platformKey
	 * @param loginId
	 * @return
	 */
	int countByPlatformAndDeleteTimeIsNullAndLoginIdAndParentIdNot(String platformKey, String loginId, Long parentId);
}
