package cn.cerestech.framework.support.starter.console.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.framework.support.starter.console.entity.SysPrivilege;

public interface SysPrivilegeDao extends JpaRepository<SysPrivilege, Long> {

	public SysPrivilege findUniqueByPlatformAndEmployeeId(String platform, Long eid);

}
