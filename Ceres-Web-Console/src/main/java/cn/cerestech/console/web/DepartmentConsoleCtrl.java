package cn.cerestech.console.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;

import cn.cerestech.console.annotation.LoginRequired;
import cn.cerestech.console.entity.SysDepartment;
import cn.cerestech.console.entity.SysDepartmentEmployee;
import cn.cerestech.console.enums.Console;
import cn.cerestech.console.errorcodes.DepartmentErrorCodes;
import cn.cerestech.console.service.DepartmentService;
import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.configuration.service.ConfigService;

@RequestMapping("$$ceres_sys/console/department")
@Controller
public class DepartmentConsoleCtrl extends WebConsoleSupport {

	@Autowired
	DepartmentService departmentService;

	@Autowired
	ConfigService configService;

	@RequestMapping("init")
	@LoginRequired
	public @ResponseBody void init() {
		List<SysDepartment> structured = departmentService.structuredFlatDepartments();
		String json = KV.on().put("deps", structured).toJson();
		log.trace(json);
		zipOut(json);
	}

	@RequestMapping("edit")
	@LoginRequired
	public @ResponseBody void edit(@RequestParam(value = "id", required = false) Long id) {
		KV retMap = KV.on();
		if (id != null) {
			retMap.put("dept", mysqlService.queryById(SysDepartment.class, id));
			retMap.put("deptEmps", mysqlService.queryBy(SysDepartmentEmployee.class, " department_id=" + id));
		}
		zipOut(retMap.toJson());
	}

	@RequestMapping("update")
	@LoginRequired
	public @ResponseBody void update(SysDepartment dept) {
		if (dept != null) {
			if (dept.getId() == null) {
				mysqlService.insert(dept);
			} else {
				mysqlService.update(dept);
			}
			departmentService.resort(dept.getParent_id());
			zipOut(Result.success(dept).toJson());
		} else {
			zipOut(Result.error(DepartmentErrorCodes.DEPARTMENT_NOT_EXIST).toJson());
		}
	}

	@RequestMapping("rename")
	@LoginRequired
	public @ResponseBody void update(@RequestParam(value = "id") Long id, @RequestParam(value = "name") String name) {
		if (!Strings.isNullOrEmpty(name) && id != null) {
			SysDepartment dept = mysqlService.queryById(SysDepartment.class, id);
			if (dept != null) {
				dept.setName(name);
				mysqlService.update(dept);
				zipOut(Result.success(dept).toJson());
				return;
			}
		}
		zipOut(Result.error(DepartmentErrorCodes.DEPARTMENT_NOT_EXIST).toJson());
	}

	@RequestMapping("move")
	@LoginRequired
	public @ResponseBody void move(@RequestParam(value = "id") Long id, @RequestParam(value = "move") String moveStr) {

		if (!Strings.isNullOrEmpty(moveStr) && id != null) {
			EnumCollector collector = EnumCollector.forClass(Console.Department.Move.class);
			Console.Department.Move move = collector.keyOf(moveStr);
			if (move != null) {
				Result<SysDepartment> ret = departmentService.move(id, move);
				zipOut(ret.toJson());
				return;
			}
		}

		zipOut(Result.error(DepartmentErrorCodes.DEPARTMENT_NOT_EXIST).toJson());
	}

	@RequestMapping("remove")
	@LoginRequired
	public @ResponseBody void remove(@RequestParam(value = "id") Long id) {
		zipOut(departmentService.remove(id).toJson());
	}
}
