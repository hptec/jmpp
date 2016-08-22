package cn.cerestech.framework.support.starter.console.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.framework.support.starter.console.entity.SysMenu;

public interface SysMenuDao extends JpaRepository<SysMenu, Long> {

	public List<SysMenu> findByParentIsNull();

	/**
	 * 根据Key找到对应的菜单，被删除的菜单也有可能被找到。
	 * 
	 * @param platform
	 * @param uuid
	 * @return
	 */
	public SysMenu findUniqueByPlatformAndUuid(String platform, String uuid);

	public List<SysMenu> findByPlatformAndUuidIn(String platform, Collection<String> sets);

}
