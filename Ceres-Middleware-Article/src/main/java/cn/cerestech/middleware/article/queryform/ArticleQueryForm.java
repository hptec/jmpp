package cn.cerestech.middleware.article.queryform;

import cn.cerestech.console.queryform.QueryForm;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.middleware.article.service.ArticleService;

public class ArticleQueryForm extends QueryForm {

	public ArticleQueryForm() {
		setTitle("文章管理");

		addColumnImage("cover_image", "封面", 18, 18);
		addColumnText("author", "作者");
		addColumnText("title", "标题");
		addColumnEnum("category", "分类",
				EnumCollector.forClass(EnumCollector.queryClassByCategory(ArticleService.CATEGORY_KEYWORD)).getClazz());
		addColumnText("source", "来源");
		addColumnEnum("visible", "是否可见", YesNo.class);
		addColumnText("view_count", "浏览量");
		addColumnDate("publish_time", "发布时间");

		addTermEnum("category", "-请选择分类-", null, EnumCollector.queryClassByCategory(ArticleService.CATEGORY_KEYWORD));
		addTermEnum("visible", "-请选择是否可见-", null, YesNo.class);
		addTermDateRange("fromto", "请选择日期范围", null);

		addToolButton("fa fa-plus", "", null, "#/article/update");
		addRowButton("fa fa-edit", null, null, "#/article/update?id={{row.id}}");
		addRowButton("fa fa-trash", null, "remove(row)", null);
	}

}
