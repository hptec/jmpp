package cn.cerestech.console.queryform.forms;

import cn.cerestech.console.queryform.QueryForm;
import cn.cerestech.framework.core.enums.YesNo;

public class RoleQueryForm extends QueryForm {

	public RoleQueryForm() {
		setTitle("角色管理");

		addColumnText("name", "角色名称");
		addColumnEnum("super_admin", "超级管理员", YesNo.class);
		addColumnText("employee_count", "员工数");
		addColumnText("menu_count", "菜单数");
		addColumnDate("create_time", "创建时间", null);

		addToolButton("fa fa-plus", null, null, "#/enterprize/authorize/role/edit?id=");

		addRowButton("fa fa-edit", null, null, "#/enterprize/authorize/role/edit?id={{row.id}}");
		addRowButton("fa fa-trash", null, "remove(row)", null);

		addTermEnum("super_admin", "是否超级管理员", null, YesNo.class);
	}

}
