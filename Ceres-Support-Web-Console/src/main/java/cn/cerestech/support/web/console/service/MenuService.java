package cn.cerestech.support.web.console.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.web.enums.ModuleType;
import cn.cerestech.framework.support.web.service.ManifestService;
import cn.cerestech.support.web.console.dao.SysMenuDao;
import cn.cerestech.support.web.console.entity.SysMenu;

@Service
public class MenuService {
	// TODO 菜单的同步，排序ENTITY
	@Autowired
	ManifestService manifestService;

	@Autowired
	SysMenuDao sysMenuDao;

	// 记录当前platform是否已经同步过菜单
	private static Map<Long, Boolean> platformMenuSynchronizedPool = Maps.newHashMap();

	public List<Jsons> getFlatManifestMenus() {
		Set<String> keyPool = Sets.newHashSet();

		List<Jsons> menus = manifestService.getModule(ModuleType.MENU);
		menus.stream().forEach(json -> {
			if (!json.get("key").isNull()) {
				String uuid = json.get("key").asString();
				if (keyPool.contains(uuid)) {
					throw new IllegalArgumentException("Menu conflict: " + json.toPrettyJson());
				} else {
					keyPool.add(uuid);
				}

			}

		});
		return menus;
	}

	/**
	 * 读取登录用户的菜单
	 * 
	 * @return
	 */
	public List<SysMenu> getMyMenus() {
		doSynchronizedIfNecessary();
		return sysMenuDao.findByParentIsNull();
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

		// 获取Manifest中配置的菜单
		List<Jsons> manifestJsons = getFlatManifestMenus();
		manifestJsons.forEach(json -> {
			// 添加新的菜单进入数据库
			String uuid = json.get("key").asString();
			SysMenu menu = sysMenuDao.findByUuid(uuid);
			if (menu == null) {
				// 数据库中不存在，加入数据库
				// 转化json -> SysMenu
				SysMenu me = new SysMenu();
				me.setCaption(json.get("caption").isNull() ? null : json.get("caption").asString());
				me.setIcon(json.get("icon").isNull() ? null : json.get("icon").asString());
				if (!json.get("parent").isNull()) {
					SysMenu parent = sysMenuDao.findByUuid(json.get("parent").asString());
					if (parent != null) {
						me.setParent(parent);
						if (parent.getSubmenus() == null) {
							parent.setSubmenus(Lists.newArrayList());
						}
						parent.getSubmenus().add(me);
					}
				}
				me.setSortIndex(999);
				me.setUuid(json.get("key").asString());
				me.setUri(json.get("uri").asString(null));

				sysMenuDao.save(me);
			}
		});

		// 删除多余的菜单
		Set<String> keySet = Sets.newHashSet();
		manifestJsons.forEach(json -> {
			keySet.add(json.get("key").asString());
		});

		List<SysMenu> menusInDb = sysMenuDao.findAll();
		menusInDb.forEach(m -> {
			if (!keySet.contains(m.getUuid())) {
				sysMenuDao.delete(m);
			}
		});

		// Set<String> allKeyInManifest = flatMenus.values().stream().map(m ->
		// m.getKey()).distinct()
		// .collect(Collectors.toSet());
		//
		// List<SysMenu> allInDb = sysMenuDao.findAll();
		// Set<String> allKeyInDb = allInDb.stream().map(m ->
		// m.getKey()).distinct().collect(Collectors.toSet());
		//
		// // 要删除的menu
		// Set<String> keyToDelete = allKeyInDb.stream().filter(k ->
		// !allKeyInManifest.contains(k))
		// .collect(Collectors.toSet());
		// sysMenuDao.deleteByKeyIn(keyToDelete);
		//
		// // 要添加的Menu
		// List<SysMenu> menuToAdd = allKeyInManifest.stream().filter(k ->
		// !allKeyInDb.contains(k))
		// .map(k -> flatMenus.get(k)).collect(Collectors.toList());
		//
		// sysMenuDao.save(menuToAdd);
		//
		// platformMenuSynchronizedPool.put(1L, Boolean.TRUE);
	}

}
