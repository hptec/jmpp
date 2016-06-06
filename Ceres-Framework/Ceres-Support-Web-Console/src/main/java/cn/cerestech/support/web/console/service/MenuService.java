package cn.cerestech.support.web.console.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.web.enums.ModuleType;
import cn.cerestech.framework.support.web.service.ManifestService;
import cn.cerestech.support.web.console.dao.SysMenuDao;
import cn.cerestech.support.web.console.entity.SysMenu;

@Service
public class MenuService {
	//TODO 菜单的同步，排序ENTITY
	@Autowired
	ManifestService manifestService;

	@Autowired
	SysMenuDao sysMenuDao;

	// 记录当前platform是否已经同步过菜单
	private static Map<Long, Boolean> platformMenuSynchronizedPool = Maps.newHashMap();

	/**
	 * 获取Manifest中配置的所有菜单
	 * 
	 * @return
	 */
	public Map<String, SysMenu> getFlatDefaultMenus() {
		Map<String, SysMenu> buffer = Maps.newHashMap();

		manifestService.getModule(ModuleType.MENU).stream().map(json -> json.to(SysMenu.class)).forEach(m -> {
			if (buffer.containsKey(m.getKey())) {
				throw new IllegalArgumentException("Menu conflict: " + Jsons.from(m).toPrettyJson());
			} else {
				buffer.put(m.getKey(), m);
			}
		});
		return buffer;
	}

	/**
	 * 读取登录用户的菜单
	 * 
	 * @return
	 */
	public List<SysMenu> getMyMenus() {
		doSynchronizedIfNecessary();
		return sysMenuDao.findAll();
	}

	/**
	 * 如果有必要需要同步系统菜单
	 * 
	 * @return
	 */
	public void doSynchronizedIfNecessary() {

		if (!platformMenuSynchronizedPool.isEmpty()) {
			// 已经同步过了，无需同步，直接返回
			return;
		}

		// 同步菜单
		Map<String, SysMenu> flatMenus = getFlatDefaultMenus();
		// 排除掉已经存在的
		sysMenuDao.findAll().forEach(m -> {
			flatMenus.remove(m.getKey());
		});

		sysMenuDao.save(flatMenus.values());

		platformMenuSynchronizedPool.put(1L, Boolean.TRUE);
	}

}
