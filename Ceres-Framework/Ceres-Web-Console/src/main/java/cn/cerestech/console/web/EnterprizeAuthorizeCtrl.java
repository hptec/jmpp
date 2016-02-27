package cn.cerestech.console.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cerestech.console.annotation.LoginRequired;
import cn.cerestech.console.entity.Employee;
import cn.cerestech.console.entity.SysMenu;
import cn.cerestech.console.entity.SysRole;
import cn.cerestech.console.errorcodes.SysRoleErrorCodes;
import cn.cerestech.console.service.PrivilegeService;
import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.framework.support.configuration.service.ConfigService;

@RequestMapping("$$ceres_sys/console/enterprize/authorize")
@Controller
public class EnterprizeAuthorizeCtrl extends WebConsoleSupport {

	@Autowired
	ConfigService configService;

	@Autowired
	PrivilegeService privilegeService;

	@RequestMapping("menu/init")
	@LoginRequired
	public @ResponseBody void menuInit() {
		KV kv = KV.on();
		kv.put("menus", mysqlService.queryBy(SysMenu.class, " 1=1 ORDER BY sort_index"));

		zipOut(Jsons.toJson(kv));
	}

	@RequestMapping("menu/search")
	@LoginRequired
	public @ResponseBody void menuSearch(@RequestParam(value = "pid", required = false) Long pid) {
		StringBuffer where = new StringBuffer();
		if (pid != null) {
			where.append("parent_id = " + pid);
		} else {
			where.append(" 1=1");
		}

		where.append(" ORDER BY sort_index");

		List<SysMenu> retList = mysqlService.queryBy(SysMenu.class, where.toString());

		zipOut(Jsons.toJson(retList));
	}

	@RequestMapping("menu/recoverall")
	@LoginRequired
	public @ResponseBody void menuRecoverAll() {
		Result<List<SysMenu>> ret = privilegeService.menuRecoverAll();
		zipOut(ret.toJson());
	}

	@RequestMapping("menu")
	@LoginRequired
	public @ResponseBody void menu(@RequestParam(value = "id") Long id) {
		KV retMap = KV.on();
		if (id != null) {
			retMap.put("menu", mysqlService.queryById(SysMenu.class, id));
		}

		retMap.put("all_menu", mysqlService.queryBy(SysMenu.class, " 1=1 ORDER BY sort_index"));
		zipOut(Jsons.toJson(retMap));
	}

	@RequestMapping("menu/update")
	@LoginRequired
	public @ResponseBody void menuUpdate(SysMenu menu) {

		if (menu != null) {
			if (menu.getParent_id() == null) {
				menu.setParent_id(-1L);
			}

			mysqlService.update(menu);
		}

		zipOut(Result.success().toJson());
	}

	@RequestMapping("menu/recover")
	@LoginRequired
	public @ResponseBody void menuRecover(@RequestParam(value = "id") Long id) {
		SysMenu mFactory = (SysMenu) BaseEntity.getInit(SysMenu.class, id);
		if (mFactory != null) {
			mysqlService.update(mFactory);
		}
		zipOut(Result.success().toJson());
	}

	@RequestMapping("menu/select")
	@LoginRequired
	public @ResponseBody void menuSelect() {
		KV kv = KV.on();
		kv.put("menus", mysqlService.queryBy(SysMenu.class, " 1=1 ORDER BY sort_index"));

		zipOut(Jsons.toJson(kv));
	}

	@RequestMapping("role/search")
	@LoginRequired
	public @ResponseBody void roleSearch(@RequestParam(value = "super_admin", required = false) String isSuperAdmin) {
		List<SysRole> retList = privilegeService.searchRole(isSuperAdmin);
		zipOut(Jsons.toJson(retList));
	}

	@RequestMapping("role")
	@LoginRequired
	public @ResponseBody void role(@RequestParam(value = "id", required = false) Long id) {
		KV retMap = KV.on();
		retMap.put("role", privilegeService.queryRoleFull(id));

		retMap.put("all_menu", mysqlService.queryBy(SysMenu.class, " 1=1 ORDER BY sort_index"));
		retMap.put("all_employee", mysqlService.queryBy(Employee.class));
		zipOut(Jsons.toJson(retMap));
	}

	@RequestMapping("role/update")
	@LoginRequired
	public @ResponseBody void roleUpdate(@RequestParam(value = "json") String json) {

		SysRole role = Jsons.fromJson(json, SysRole.class);
		if (role != null) {
			zipOut(privilegeService.persistRole(role).toJson());
		} else {
			zipOut(Result.error(SysRoleErrorCodes.ROLE_IS_EMPTY).toJson());
		}

	}

	@RequestMapping("role/remove")
	@LoginRequired
	public @ResponseBody void roleRemove(@RequestParam(value = "id") Long id) {
		zipOut(privilegeService.removeRole(id).toJson());

	}
}
