package cn.cerestech.framework.support.starter.console.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.support.starter.console.dao.SysRoleDao;
import cn.cerestech.framework.support.starter.console.entity.SysRole;
import cn.cerestech.framework.support.starter.provider.PlatformProvider;

@Service
public class RoleService {

	@Autowired
	SysRoleDao roleDao;

	@Autowired
	PlatformProvider platformProvider;

	/**
	 * 获取菜单
	 * 
	 * @param key
	 * @return
	 */
	public SysRole getByKey(String key) {
		String platformKey = platformProvider.get().getKey();
		return roleDao.findUniqueByPlatformAndUuid(platformKey, key);
	}

	/**
	 * 保存菜单
	 * 
	 * @param menu
	 * @return
	 */
	public SysRole update(SysRole role) {
		String platformKey = platformProvider.get().getKey();
		role.setPlatform(platformKey);
		roleDao.save(role);
		return role;
	}

	public void delete(Long id) {
		if (id != null) {
			roleDao.deleteByPlatformAndId(platformProvider.get().getKey(), id);
		}
	}

	/**
	 * 得到系统中配置的超级管理员角色
	 * 
	 * @return
	 */
	public List<SysRole> getSystemSuperAdminRoles() {
		return roleDao.findByPlatformAndIsSuperAdmin(platformProvider.get().getKey(), YesNo.YES);
	}

	public List<SysRole> getRolesById(Collection<Long> idIn) {
		return roleDao.findByPlatformAndIdIn(platformProvider.get().getKey(), idIn);
	}

	public List<SysRole> getRolesByKey(Collection<String> keyIn) {
		return roleDao.findByPlatformAndUuidIn(platformProvider.get().getKey(), keyIn);
	}
}
