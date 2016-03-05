package cn.cerestech.console.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;

import cn.cerestech.console.annotation.LoginRequired;
import cn.cerestech.console.entity.Employee;
import cn.cerestech.console.entity.SysPosition;
import cn.cerestech.console.errorcodes.PositionErrorCodes;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.configuration.service.ConfigService;

@RequestMapping("$$ceres_sys/console/position")
@Controller
public class PositionConsoleCtrl extends WebConsoleSupport {

	@Autowired
	ConfigService configService;

	@RequestMapping("search")
	@LoginRequired
	public @ResponseBody void search() {
		List<SysPosition> retList = mysqlService.queryBy(SysPosition.class);
		zipOut(Jsons.toJson(retList));
	}

	@RequestMapping("add")
	@LoginRequired
	public @ResponseBody void add(@RequestParam(value = "name") String name) {
		if (!Strings.isNullOrEmpty(name)) {
			SysPosition pos = new SysPosition();
			pos.setName(name);
			pos.setCreate_time(new Date());
			mysqlService.insert(pos);
			zipOut(Result.success(pos).toJson());
		} else {
			zipOut(Result.error().setMessage("名称未设置").toJson());
		}
	}

	@RequestMapping("rename")
	@LoginRequired
	public @ResponseBody void rename(@RequestParam(value = "name") String name, @RequestParam(value = "id") Long id) {
		if (!Strings.isNullOrEmpty(name) && id != null) {
			SysPosition pos = mysqlService.queryById(SysPosition.class, id);
			if (pos != null) {
				pos.setName(name);
				mysqlService.update(pos);
				zipOut(Result.success(pos).toJson());
			}
		}
		zipOut(Result.error().setMessage("名称未设置").toJson());
	}

	@RequestMapping("remove")
	@LoginRequired
	public @ResponseBody void remove(@RequestParam(value = "id") Long id) {
		if (id != null) {
			SysPosition pos = mysqlService.queryById(SysPosition.class, id);
			if (pos != null) {
				// 员工不能有引用此岗位的
				List<Employee> eList = mysqlService.queryBy(Employee.class, " position_id=" + pos.getId());
				if (eList.size() > 0) {
					zipOut(Result.error(PositionErrorCodes.POSITION_USED_BY_EMPLOYEE).toJson());
					return;
				} else {
					mysqlService.delete(SysPosition.class, id);
				}
			}
		}
		zipOut(Result.success().toJson());
	}

}
