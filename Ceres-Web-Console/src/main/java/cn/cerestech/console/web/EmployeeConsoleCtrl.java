package cn.cerestech.console.web;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.cerestech.console.annotation.LoginRequired;
import cn.cerestech.console.entity.Employee;
import cn.cerestech.console.entity.SysDepartment;
import cn.cerestech.console.entity.SysPosition;
import cn.cerestech.console.interceptor.ConsoleLoginIcptr;
import cn.cerestech.console.service.EmployeeService;
import cn.cerestech.framework.core.Encrypts;
import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.enums.Gender;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.configuration.service.ConfigService;
import cn.cerestech.framework.support.web.Cookies;
import cn.cerestech.framework.web.enums.WebConfigKey;

@RequestMapping("$$ceres_sys/console/employee")
@Controller
public class EmployeeConsoleCtrl extends WebConsoleSupport {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	ConfigService configService;

	@RequestMapping("/login/init")
	public @ResponseBody void login() {
		Map<String, Object> retMap = Maps.newHashMap();
		retMap.put("SITE_SHORT_NAME", configService.query(WebConfigKey.SITE_SHORT_NAME).stringValue());
		zipOut(Jsons.toJson(retMap));
	}

	@RequestMapping("/profile/init")
	@LoginRequired
	public @ResponseBody void profileModifyInit(@RequestParam(value = "id", required = false) Long id) {
		KV retMap = KV.on();
		retMap.put("info", mysqlService.queryById(Employee.class, id));
		retMap.put("enumFrozen", EnumCollector.forClass(YesNo.class).toList());
		retMap.put("enumGender", EnumCollector.forClass(Gender.class).toList());
		retMap.put("allDepts", mysqlService.queryBy(SysDepartment.class));
		retMap.put("allPos", mysqlService.queryBy(SysPosition.class));

		if (id == null) {
			// 新增模式
		} else if (id == userId()) {
			// 修改自己

		} else {
			// 修改他人
		}

		zipOut(Jsons.toJson(retMap));
	}

	@RequestMapping("/doLogin")
	public @ResponseBody void doLogin(@RequestParam("usr") String usr, @RequestParam("pwd") String pwd,
			@RequestParam("remember") Boolean remember) {
		Result<Employee> ret = employeeService.login(usr, pwd, remember);
		if (ret.isSuccess()) {
			// 记录登录信息到cookie中
			Employee e = ret.getObject();
			session(ConsoleLoginIcptr.SK_CONSOLE_USER_ID, e.getId());// 记录入Session
			session(ConsoleLoginIcptr.SK_CONSOLE_USER_OBJ, e);// 记录入Session
			Cookies c = Cookies.on(getRequest(), getResponse());
			// 如果用户记住登录，还要放入cookie
			if (remember) {
				// 记录登录,添加cookie
				c.add(ConsoleLoginIcptr.COOKIE_CONSOLE_USER_ID, e.getId().toString());
				c.add(ConsoleLoginIcptr.COOKIE_CONSOLE_USER_REMEMBER, e.getRemember_token());
			} else {
				// 清除cookie
				c.remove(ConsoleLoginIcptr.COOKIE_CONSOLE_USER_ID);
				c.remove(ConsoleLoginIcptr.COOKIE_CONSOLE_USER_REMEMBER);
			}
			c.flush();
			ret.setObject(null);
		}
		zipOut(ret.toJson());
	}

	@RequestMapping("/doLogout")
	public @ResponseBody void doLogout() {
		getRequest().getSession().invalidate();
		Cookies.on(getRequest(), getResponse()).remove(ConsoleLoginIcptr.COOKIE_CONSOLE_USER_ID)
				.remove(ConsoleLoginIcptr.COOKIE_CONSOLE_USER_REMEMBER).flush();
		zipOut(Result.success().toJson());
	}

	@RequestMapping("/doModiPwd")
	@LoginRequired
	public @ResponseBody void doModiPwd(@RequestParam(value = "pwd") String pwd) {
		zipOut(employeeService.modifyPassword(userId(), pwd).setObject(null).toJson());
	}

	@RequestMapping("/doModiProfile")
	@LoginRequired
	public @ResponseBody void doModiProfile(Employee e) {
		if (e.getId() == null) {
			e.setCreate_time(new Date());
			e.setPwd(Encrypts.md5("111111"));
			mysqlService.insert(e);
		} else {
			mysqlService.update(e);
		}
		zipOut(Result.success().toJson());
	}

	@RequestMapping("/search")
	@LoginRequired
	public @ResponseBody void search(@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "frozen", required = false) String frozen,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "scope", required = false) String scope,
			@RequestParam(value = "fromto", required = false) String fromto) {
		Gender enumGender = null;
		if (!Strings.isNullOrEmpty(gender)) {
			enumGender = EnumCollector.forClass(Gender.class).keyOf(gender);
		}

		YesNo enumFrozen = null;
		if (!Strings.isNullOrEmpty(frozen)) {
			enumFrozen = EnumCollector.forClass(YesNo.class).keyOf(frozen);
		}
		// keyword 直接使用

		String dateFrom = null, dateTo = null;
		Date dateRegisterFrom = null, dateRegisterTo = null, dateLoginFrom = null, dateLoginTo = null;
		if (!Strings.isNullOrEmpty(fromto)) {
			List<String> strFromTo = Splitter.on(" ").splitToList(fromto);
			dateFrom = strFromTo.get(0);
			dateTo = strFromTo.get(2);
		}

		if (!Strings.isNullOrEmpty(scope)) {
			if ("R".equals(scope)) {
				try {
					if (!Strings.isNullOrEmpty(dateFrom)) {
						dateRegisterFrom = FORMAT_DATE.parse(dateFrom);
					}
					if (!Strings.isNullOrEmpty(dateTo)) {
						dateRegisterTo = FORMAT_DATE.parse(dateTo);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				try {
					if (!Strings.isNullOrEmpty(dateFrom)) {
						dateLoginFrom = FORMAT_DATE.parse(dateFrom);
					}
					if (!Strings.isNullOrEmpty(dateTo)) {
						dateLoginTo = FORMAT_DATE.parse(dateTo);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

		}

		List<Employee> retList = employeeService.search(enumGender, enumFrozen, keyword, dateRegisterFrom,
				dateRegisterTo, dateLoginFrom, dateLoginTo);

		KV retMap = KV.on();
		retMap.put("data", retList);
		retMap.put("allPos", mysqlService.queryBy(SysPosition.class));
		retMap.put("allDepts", mysqlService.queryBy(SysDepartment.class));
		zipOut(retMap);
	}
}
