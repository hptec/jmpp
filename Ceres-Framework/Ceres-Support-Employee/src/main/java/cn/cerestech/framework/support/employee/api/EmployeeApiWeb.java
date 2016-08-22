package cn.cerestech.framework.support.employee.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.employee.criteria.EmployeeCriteria;
import cn.cerestech.framework.support.employee.dao.EmployeeDao;
import cn.cerestech.framework.support.employee.entity.Employee;
import cn.cerestech.framework.support.employee.service.EmployeeService;
import cn.cerestech.framework.support.login.annotation.LoginRequired;
import cn.cerestech.framework.support.login.operator.UserSessionOperator;
import cn.cerestech.framework.support.starter.provider.PlatformProvider;
import cn.cerestech.framework.support.starter.web.WebSupport;

@RequestMapping("/api/employee")
@RestController
public class EmployeeApiWeb extends WebSupport implements UserSessionOperator {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	PlatformProvider platformProvider;

	@Autowired
	EmployeeDao employeeDao;

	/**
	 * 得到当前登录用户信息
	 * 
	 * @param id
	 */
	@RequestMapping("/me")
	@LoginRequired
	public void me() {
		Long id = getUserId();
		if (id == null) {
			zipOutRequireJson(null);
			return;
		} else {
			Employee me = employeeService.get(id);
			zipOutRequireJson(me == null ? null : me.safty());
			return;
		}
	}

	@RequestMapping("/get")
	public void get(@RequestParam("id") Long id) {
		Employee e = employeeService.get(id);
		zipOut(Jsons.from(e == null ? "{}" : e.safty()));
	}

	/**
	 * 用于后台检索体系内管理员
	 * 
	 * @param criteria
	 */
	@RequestMapping("/search")
	@LoginRequired
	public void search(EmployeeCriteria criteria) {
		criteria.setPlatformKey(platformProvider.get().getKey());
		criteria.setIsSuperAdmin(YesNo.NO.key());
		criteria.setParentId(getUserId());
		criteria.doSearch(employeeDao);
		zipOut(Result.success(criteria));
	}

	@RequestMapping("/updateSub")
	@LoginRequired
	public void update(@RequestParam("jsonStr") String jsonStr) {
		Employee e = Jsons.from(jsonStr).to(Employee.class);
		Result<Employee> ret = employeeService.updateSubEmployee(e);
		zipOut(ret.isSuccess() ? Result.success(ret.getObject().safty()) : ret);
	}

}
