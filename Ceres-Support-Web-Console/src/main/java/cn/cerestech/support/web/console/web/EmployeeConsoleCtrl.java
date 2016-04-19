package cn.cerestech.support.web.console.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cerestech.support.web.console.service.EmployeeService;

@RequestMapping("$$ceres_sys/console/employee")
@Controller
public class EmployeeConsoleCtrl extends AbstractConsoleWeb {

	@Autowired
	EmployeeService employeeService;

	//
	//
	// @RequestMapping("/profile/init")
	// @LoginRequired
	// public @ResponseBody void profileModifyInit(@RequestParam(value = "id",
	// required = false) Long id) {
	// KV retMap = KV.on();
	// retMap.put("info", mysqlService.queryById(Employee.class, id));
	// retMap.put("enumFrozen", EnumCollector.forClass(YesNo.class).toList());
	// retMap.put("enumGender", EnumCollector.forClass(Gender.class).toList());
	// retMap.put("allDepts", mysqlService.queryBy(SysDepartment.class));
	// retMap.put("allPos", mysqlService.queryBy(SysPosition.class));
	//
	// if (id == null) {
	// // 新增模式
	// } else if (id == userId()) {
	// // 修改自己
	//
	// } else {
	// // 修改他人
	// }
	//
	// zipOut(Jsons.toJson(retMap));
	// }
	//
	//
	// @RequestMapping("/doModiPwd")
	// @LoginRequired
	// public @ResponseBody void doModiPwd(@RequestParam(value = "pwd") String
	// pwd) {
	// zipOut(employeeService.modifyPassword(userId(),
	// pwd).setObject(null).toJson());
	// }
	//
	// @RequestMapping("/doModiProfile")
	// @LoginRequired
	// public @ResponseBody void doModiProfile(Employee e) {
	// if (e.getId() == null) {
	// e.setCreate_time(new Date());
	// e.setPwd(Encrypts.md5("111111"));
	// mysqlService.insert(e);
	// } else {
	// mysqlService.update(e);
	// }
	// zipOut(Result.success().toJson());
	// }
	//
	// @RequestMapping("/search")
	// @LoginRequired
	// public @ResponseBody void search(@RequestParam(value = "gender", required
	// = false) String gender,
	// @RequestParam(value = "frozen", required = false) String frozen,
	// @RequestParam(value = "keyword", required = false) String keyword,
	// @RequestParam(value = "scope", required = false) String scope,
	// @RequestParam(value = "fromto", required = false) String fromto) {
	// Gender enumGender = null;
	// if (!Strings.isNullOrEmpty(gender)) {
	// enumGender = EnumCollector.forClass(Gender.class).keyOf(gender);
	// }
	//
	// YesNo enumFrozen = null;
	// if (!Strings.isNullOrEmpty(frozen)) {
	// enumFrozen = EnumCollector.forClass(YesNo.class).keyOf(frozen);
	// }
	// // keyword 直接使用
	//
	// String dateFrom = null, dateTo = null;
	// Date dateRegisterFrom = null, dateRegisterTo = null, dateLoginFrom =
	// null, dateLoginTo = null;
	// if (!Strings.isNullOrEmpty(fromto)) {
	// List<String> strFromTo = Splitter.on(" ").splitToList(fromto);
	// dateFrom = strFromTo.get(0);
	// dateTo = strFromTo.get(2);
	// }
	//
	// if (!Strings.isNullOrEmpty(scope)) {
	// if ("R".equals(scope)) {
	// try {
	// if (!Strings.isNullOrEmpty(dateFrom)) {
	// dateRegisterFrom = FORMAT_DATE.parse(dateFrom);
	// }
	// if (!Strings.isNullOrEmpty(dateTo)) {
	// dateRegisterTo = FORMAT_DATE.parse(dateTo);
	// }
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// } else {
	// try {
	// if (!Strings.isNullOrEmpty(dateFrom)) {
	// dateLoginFrom = FORMAT_DATE.parse(dateFrom);
	// }
	// if (!Strings.isNullOrEmpty(dateTo)) {
	// dateLoginTo = FORMAT_DATE.parse(dateTo);
	// }
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// }
	//
	// List<Employee> retList = employeeService.search(enumGender, enumFrozen,
	// keyword, dateRegisterFrom,
	// dateRegisterTo, dateLoginFrom, dateLoginTo);
	//
	// KV retMap = KV.on();
	// retMap.put("data", retList);
	// retMap.put("allPos", mysqlService.queryBy(SysPosition.class));
	// retMap.put("allDepts", mysqlService.queryBy(SysDepartment.class));
	// zipOut(retMap);
	// }
}
