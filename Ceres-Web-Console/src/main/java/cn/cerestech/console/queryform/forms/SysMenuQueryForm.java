package cn.cerestech.console.queryform.forms;

import cn.cerestech.console.enums.Console;
import cn.cerestech.console.queryform.QueryForm;

public class SysMenuQueryForm extends QueryForm {

	public SysMenuQueryForm() {
		setTitle("菜单管理");

		addColumnText("id", "菜单ID");
		addColumnIcon("icon", "图标");
		addColumnText("title", "标题");
		addColumnText("sort_index", "排序");
		addColumnEnum("is_menu", "类型", Console.SysMenu.Type.class);

		addToolButton("fa fa-fw fa-refresh", "恢复为出厂数据", "recoverAll()", null);

		addRowButton("fa fa-edit", null, null, "#/enterprize/authorize/menu/{{row.id}}");

	}

}
