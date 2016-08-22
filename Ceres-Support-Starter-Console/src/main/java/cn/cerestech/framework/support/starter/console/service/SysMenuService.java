package cn.cerestech.framework.support.starter.console.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.support.starter.console.dao.SysMenuDao;
import cn.cerestech.framework.support.starter.console.entity.SysMenu;
import cn.cerestech.framework.support.starter.provider.PlatformProvider;

@Service
public class SysMenuService {

	@Autowired
	SysMenuDao sysMenuDao;

	@Autowired
	PlatformProvider platformProvider;

	/**
	 * 获取菜单
	 * 
	 * @param key
	 * @return
	 */
	public SysMenu getByKey(String key) {
		String platformKey = platformProvider.get().getKey();
		return sysMenuDao.findUniqueByPlatformAndUuid(platformKey, key);
	}

	/**
	 * 保存菜单
	 * 
	 * @param menu
	 * @return
	 */
	public SysMenu update(SysMenu menu) {
		String platformKey = platformProvider.get().getKey();
		menu.setPlatform(platformKey);
		sysMenuDao.save(menu);
		return menu;
	}

	/**
	 * 删除一个菜单
	 * 
	 * @param menuId
	 */
	public void delete(Long menuId) {
		if (menuId != null) {
			sysMenuDao.delete(menuId);
		}
	}
}
