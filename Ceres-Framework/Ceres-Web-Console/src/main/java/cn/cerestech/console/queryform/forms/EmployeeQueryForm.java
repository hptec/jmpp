package cn.cerestech.console.queryform.forms;

import cn.cerestech.console.enums.Console.Employee.Scope;
import cn.cerestech.console.queryform.QueryForm;
import cn.cerestech.framework.core.enums.Gender;
import cn.cerestech.framework.core.enums.YesNo;

public class EmployeeQueryForm extends QueryForm {

	public EmployeeQueryForm() {
		setTitle("员工名录");

		addColumnImage("avatar_img", "员工头像", 18, 18);
		addColumnText("nickname", "昵称");
		addColumnText("phone", "联系电话");
		addColumnEnum("gender", "性别", Gender.class);
		addColumnIdIn("department_id", "部门", "allDepts", "name");
		addColumnIdIn("position_id", "岗位", "allPos", "name");
		addColumnDate("create_time", "注册时间", null);
		addColumnDate("last_login_time", "最后登录", null);
		addColumnEnum("frozen", "冻结", YesNo.class);

		addToolButton("fa fa-plus", null, null, "#/employee/profile");

		addRowButton("fa fa-edit", null, null, "#/employee/profile?id={{row.id}}");

		addTermEnum("gender", "-请选择性别-", null, Gender.class);
		addTermEnum("frozen", "-请选择冻结状态-", null, YesNo.class);
		addTermText("keyword", "昵称、电话号码、岗位、工号", null);
		addTermEnum("scope", "-请选择时间范围-", Scope.REGISTER, Scope.class);
		addTermDateRange("fromto", "请选择日期范围", null);
	}

}
