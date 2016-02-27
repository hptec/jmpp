package cn.cerestech.middleware.helpcenter.simple.queryform;

import cn.cerestech.console.queryform.QueryForm;
import cn.cerestech.console.queryform.QueryTermRemoteList;
import cn.cerestech.framework.core.enums.YesNo;

public class HelpCenterCategoryQueryForm extends QueryForm {

	public HelpCenterCategoryQueryForm() {
		setTitle("帮助中心分类管理");

		addColumnText("name", "名称");
		addColumnIdIn("parent_id", "父级", "parent", "name");
		addColumnText("sort", "排序序号");
		addColumnEnum("visible", "是否可见", YesNo.class);
		addColumnDate("create_time", "创建时间");

		addToolButton("fa fa-plus", null, null, "#/helpcenter/category/update?id=");

		addRowButton("fa fa-edit", null, null, "#/helpcenter/category/update?id={{row.id}}");
		addRowButton("fa fa-trash", null, "remove(row)", null);

		QueryTermRemoteList remoteList = new QueryTermRemoteList();
		remoteList.setRemoteUrl("console/helpcenter/category/firstlevel");
		remoteList.setKeyName("id");
		remoteList.setDescName("name");
		remoteList.setListVarName("categoryFirstLevel");
		addTermRemoteList("parent_id", "-请选择上层分类-", null, remoteList);
	}

}
