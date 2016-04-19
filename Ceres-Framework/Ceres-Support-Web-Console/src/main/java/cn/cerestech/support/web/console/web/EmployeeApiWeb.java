package cn.cerestech.support.web.console.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.core.utils.KV;
import cn.cerestech.framework.support.login.interceptor.LoginInterceptor;
import cn.cerestech.framework.support.web.Cookies;
import cn.cerestech.support.web.console.entity.Employee;
import cn.cerestech.support.web.console.service.EmployeeService;

@RequestMapping("/api/employee")
@RestController
public class EmployeeApiWeb extends AbstractConsoleWeb {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	LoginInterceptor loginInterceptor;

	@RequestMapping("/doLogin")
	public void doLogin(@RequestParam("loginId") String usr, @RequestParam("loginPwd") String pwd,
			@RequestParam(name = "remember", defaultValue = "false") Boolean remember) {
		Result<Employee> ret = employeeService.login(usr, pwd, remember);
		if (ret.isSuccess()) {

			loginInterceptor.register(getPlatformCategory(), ret.getObject(), remember, getRequest(), getResponse());
			ret.getObject().setLoginPwd(null);
			ret.getObject().setRememberToken(null);
		}
		zipOut(ret.toJson());
	}

	@RequestMapping("/doLogout")
	public void doLogout() {
		getRequest().getSession().invalidate();
		Cookies.from(getRequest()).remove(loginInterceptor.getCookieKeyRememberID(getPlatformCategory()))
				.remove(loginInterceptor.getCookieKeyRememberToken(getPlatformCategory())).flushTo(getResponse());
		zipOut(Result.success().toJson());
	}

	@RequestMapping("/get")
	public void get(@RequestParam("id") Long id) {
		Employee e = employeeService.findById(id);
		e.setLoginPwd(null);
		e.setRememberToken(null);
		e.setRememberExpired(null);
		e.setPlatformId(null);
		zipOut(Jsons.from(e));
	}

}
