package cn.cerestech.support.web.console.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.platform.service.PlatformService;
import cn.cerestech.framework.support.service.ManifestService;
import cn.cerestech.support.web.console.dao.SysMenuDao;
import cn.cerestech.support.web.console.dao.SysMenuPageDao;
import cn.cerestech.support.web.console.entity.SysMenu;
import cn.cerestech.support.web.console.entity.SysMenuPage;

@Service
public class MenuService {

	@Autowired
	ManifestService manifestService;

	@Autowired
	PlatformService platformService;

	@Autowired
	SysMenuDao sysMenuDao;

	@Autowired
	SysMenuPageDao sysMenuPageDao;

	// 记录当前platform是否已经同步过菜单
	private static Map<Long, Boolean> platformMenuSynchronizedPool = Maps.newHashMap();

	public List<SysMenu> getDefaultMenus() {
		List<SysMenu> allMenus = Lists.newArrayList();

		manifestService.allManifest().forEach(root -> {
			allMenus.addAll(extractMenus(root, "sdf"));
		});

		return allMenus;
	}

	public Map<String, SysMenu> getFlatDefaultMenus() {
		return flatMenus(getDefaultMenus());
	}

	public Map<String, SysMenuPage> getFlatDefaultPages() {
		return flatPages(getDefaultMenus());
	}

	public List<SysMenu> getMyMenus() {
		doSynchronizedIfNecessary();
		return null;
	}

	/**
	 * 如果有必要需要同步系统菜单
	 * 
	 * @return
	 */
	public void doSynchronizedIfNecessary() {
		Long platformId = platformService.getId();

		if (platformMenuSynchronizedPool.containsKey(platformId) && platformMenuSynchronizedPool.get(platformId)) {
			// 已经同步过了，无需同步，直接返回
			return;
		}

		// 同步菜单
		Map<String, SysMenu> flatMenus = getFlatDefaultMenus();
		// 排除掉已经存在的
		sysMenuDao.findByPlatformId(platformId).forEach(m -> {
			flatMenus.remove(m.getKey());
		});

		flatMenus.forEach((k, m) -> {
			m.setPlatformId(platformId);
		});

		sysMenuDao.save(flatMenus.values());

		// 同步页面

		Map<String, SysMenuPage> flatPages = getFlatDefaultPages();
		sysMenuPageDao.findByPlatformId(platformId).forEach(p -> {
			flatPages.remove(p.getUri());
		});

		flatPages.forEach((k, p) -> {
			p.setPlatformId(platformId);
		});

		sysMenuPageDao.save(flatPages.values());

		platformMenuSynchronizedPool.put(platformId, Boolean.TRUE);
	}

	private Map<String, SysMenu> flatMenus(List<SysMenu> menus) {
		Map<String, SysMenu> flatMap = Maps.newHashMap();

		menus.forEach(menu -> {
			flatMenus(menu.getSubmenus()).forEach((k, m) -> {
				if (flatMap.containsKey(m.getKey())) {
					throw new IllegalArgumentException("Menu conflict: " + Jsons.from(m).toPrettyJson());
				} else {
					flatMap.put(m.getKey(), m);
				}
			});
			menu.getSubmenus().clear();
			if (flatMap.containsKey(menu.getKey())) {
				throw new IllegalArgumentException("Menu conflict: " + Jsons.from(menu).toPrettyJson());
			} else {
				flatMap.put(menu.getKey(), menu);
			}
		});
		return flatMap;
	}

	private Map<String, SysMenuPage> flatPages(List<SysMenu> menus) {
		Map<String, SysMenuPage> flatMap = Maps.newHashMap();
		menus.forEach(m -> {
			m.getPages().forEach(p -> {
				if (flatMap.containsKey(p.getUri())) {
					throw new IllegalArgumentException("MenuPage conflict: " + Jsons.from(p).toPrettyJson());
				} else {
					flatMap.put(p.getUri(), p);
				}
			});

			flatPages(m.getSubmenus()).forEach((k, p) -> {
				if (flatMap.containsKey(p.getUri())) {
					throw new IllegalArgumentException("MenuPage conflict: " + p.getUri());
				} else {
					flatMap.put(p.getUri(), p);
				}
			});
		});

		return flatMap;
	}

	private List<SysMenu> extractMenus(Jsons root, String parentKey) {
		List<SysMenu> thisMenus = Lists.newArrayList();

		root.get("menus").asList().forEach(json -> {

			// 提取菜单
			String key = json.get("key").asString();
			if (Strings.isNullOrEmpty(key)) {
				throw new IllegalArgumentException("The 'key' of menu is required!");
			}
			String caption = json.get("caption").asString("未知菜单");
			String parent = json.get("parent").asString(parentKey);
			String icon = json.get("icon").asString("");

			SysMenu m = new SysMenu();
			m.setCaption(caption);
			m.setIcon(icon);
			m.setKey(key);
			m.setParent(parent);
			m.setPages(extractPages(json, key));
			m.setSubmenus(extractMenus(json, key));
			thisMenus.add(m);
		});

		return thisMenus;
	}

	private List<SysMenuPage> extractPages(Jsons menu, String menuKey) {
		List<SysMenuPage> pagesList = Lists.newArrayList();
		// 提取菜单下属的页面
		menu.get("pages").asList().forEach(page -> {
			String uri = page.get("uri").asString();
			if (Strings.isNullOrEmpty(uri)) {
				throw new IllegalArgumentException("The 'uri' of pages is required!");
			}

			String tpl = page.get("tpl").asString("");
			SysMenuPage sysPage = new SysMenuPage();
			sysPage.setMenuKey(menuKey);
			sysPage.setTpl(tpl);
			sysPage.setUri(uri);
			pagesList.add(sysPage);
		});
		return pagesList;
	}

}
