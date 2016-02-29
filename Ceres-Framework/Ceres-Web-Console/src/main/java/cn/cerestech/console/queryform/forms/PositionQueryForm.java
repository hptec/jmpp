package cn.cerestech.console.queryform.forms;

import cn.cerestech.console.queryform.QueryForm;

public class PositionQueryForm extends QueryForm {

	public PositionQueryForm() {
		setTitle("岗位设置");

		addColumnText("name", "名称");
		addColumnDate("create_time", "创建时间", null);

		addToolButton("fa fa-plus", null, "add()", null);

		addRowButton("fa fa-edit", null, "rename(row)", null);
		addRowButton("fa fa-trash", null, "remove(row)", null);

	}

}
