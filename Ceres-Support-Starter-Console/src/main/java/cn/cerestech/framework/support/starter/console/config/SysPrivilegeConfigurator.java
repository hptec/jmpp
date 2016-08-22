package cn.cerestech.framework.support.starter.console.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.starter.console.entity.SysMenu;
import cn.cerestech.framework.support.starter.console.entity.SysRole;
import cn.cerestech.framework.support.starter.console.service.SysMenuService;
import cn.cerestech.framework.support.starter.console.service.RoleService;
import cn.cerestech.framework.support.starter.enums.ModuleType;
import cn.cerestech.framework.support.starter.operator.PlatformOperator;
import cn.cerestech.framework.support.starter.provider.PlatformProvider;
import cn.cerestech.framework.support.starter.service.ManifestService;

/**
 * 同步菜单
 * 
 * @author harryhe
 *
 */
@Component
public class SysPrivilegeConfigurator implements ApplicationRunner, PlatformOperator {

	@Autowired
	PlatformProvider platformProvider;

	@Autowired
	ManifestService manifestService;

	@Autowired
	SysMenuService sysMenuService;

	@Autowired
	RoleService sysRoleService;

	@Override
	public void run(ApplicationArguments arg0) throws Exception {

		// 从设置中扫描菜单
		List<Jsons> manifestJsons = manifestService.getModule(ModuleType.MENU);
		// 获取Manifest中配置的菜单
		manifestJsons.forEach(json -> {
			// 添加新的菜单进入数据库
			String uuid = json.get("key").asString();
			SysMenu menu = sysMenuService.getByKey(uuid);
			if (menu == null) {
				// 数据库中不存在，加入数据库
				// 转化json -> SysMenu
				SysMenu me = translateSysMenu(json);
				sysMenuService.update(me);
			} else {
				String mode = json.get("mode").asString(null);
				SysMenu me = translateSysMenu(json);
				if (!Strings.isNullOrEmpty(mode)) {
					switch (mode) {
					case "delete":
						// 删除这个菜单。
						sysMenuService.delete(me.getId());
						break;
					case "update":
						sysMenuService.update(me);
						break;
					}
				}
			}
		});

		// 从设置中注入角色
		manifestService.getModule(ModuleType.ROLES).forEach(json -> {
			String uuid = json.get("key").asString();
			SysRole role = sysRoleService.getByKey(uuid);
			if (role == null) {
				role = translateSysRole(json);
				sysRoleService.update(role);
			} else {
				String mode = json.get("mode").asString(null);
				SysRole me = translateSysRole(json);
				if (!Strings.isNullOrEmpty(mode)) {
					switch (mode) {
					case "delete":
						// 删除这个角色。
						sysRoleService.delete(me.getId());
						break;
					case "update":
						// 修改角色
						sysRoleService.update(me);
						break;
					}
				}
			}
		});
	}

	private SysMenu translateSysMenu(Jsons json) {
		SysMenu me = new SysMenu();
		me.setCaption(json.get("caption").isNull() ? null : json.get("caption").asString());
		me.setIcon(json.get("icon").isNull() ? null : json.get("icon").asString());
		if (!json.get("parent").isNull()) {
			SysMenu parent = sysMenuService.getByKey(json.get("parent").asString());
			if (parent != null) {
				me.setParent(parent);
				if (parent.getSubmenus() == null) {
					parent.setSubmenus(Lists.newArrayList());
				}
				parent.getSubmenus().add(me);
			}
		}
		me.setUuid(json.get("key").asString());
		me.setUri(json.get("uri").asString(null));
		List<String> privilege = json.get("privilege").asList().stream().map(j -> j.asString())
				.collect(Collectors.toList());
		me.setPrivilege(privilege);
		return me;
	}

	private SysRole translateSysRole(Jsons json) {
		SysRole role = new SysRole();
		role.setIsSuperAdmin(json.get("superAdmin").asBoolean(Boolean.FALSE) ? YesNo.YES : YesNo.NO);
		role.setUuid(json.get("key").asString());
		role.setName(json.get("name").asString());
		List<String> menus = json.get("menus").asList().stream().map(j -> j.asString()).collect(Collectors.toList());
		role.setMenus(menus);
		return role;
	}
}
