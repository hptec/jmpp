package cn.cerestech.middleware.helpcenter.simple.queryform;

import cn.cerestech.console.queryform.QueryForm;
import cn.cerestech.console.queryform.QueryTermRemoteList;
import cn.cerestech.framework.core.enums.YesNo;

public class HelpCenterQAQueryForm extends QueryForm {

	public HelpCenterQAQueryForm() {
		setTitle("帮助中心问题管理");

		addColumnText("question", "问题描述");
		addColumnIdIn("category_id", "分类", "categorys", "name");
		addColumnText("sort", "排序序号");
		addColumnEnum("visible", "是否可见", YesNo.class);

		addToolButton("fa fa-plus", null, null, "#/helpcenter/question/update?id=");

		addRowButton("fa fa-edit", null, null, "#/helpcenter/question/update?id={{row.id}}");
		addRowButton("fa fa-trash", null, "remove(row)", null);

		QueryTermRemoteList remoteList = new QueryTermRemoteList();
		remoteList.setRemoteUrl("console/helpcenter/category/search");
		remoteList.setKeyName("id");
		remoteList.setDescName("name");
		remoteList.setListVarName("categoryFlat");
		addTermRemoteList("category_id", "-请选择上层分类-", null, remoteList);
		addTermEnum("visible", "-请选择可见状态-", null, YesNo.class);
	}

}
