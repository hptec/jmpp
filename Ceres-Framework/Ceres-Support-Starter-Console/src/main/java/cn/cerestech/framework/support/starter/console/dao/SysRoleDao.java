package cn.cerestech.framework.support.starter.console.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.support.starter.console.entity.SysRole;

public interface SysRoleDao extends JpaRepository<SysRole, Long> {

	public SysRole findUniqueByPlatformAndUuid(String platform, String uuid);

	public List<SysRole> findByPlatformAndIsSuperAdmin(String platform, YesNo is);

	public List<SysRole> findByPlatformAndIdIn(String platform, Collection<Long> idIn);
	
	public void deleteByPlatformAndId(String platform,Long id);
}
