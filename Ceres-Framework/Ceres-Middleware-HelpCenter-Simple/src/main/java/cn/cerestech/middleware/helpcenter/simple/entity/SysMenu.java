package cn.cerestech.middleware.helpcenter.simple.entity;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.persistence.annotation.Table;

@SuppressWarnings("serial")
@Table("$$sys_menu")
public class SysMenu extends cn.cerestech.console.entity.SysMenu {

	static {
		addInit(new SysMenu(21L, "fa fa-fw fa-sitemap", "网站管理", "", "", "", "", -1L, 99600L));
		addInit(new SysMenu(24L, "fa fa-fw fa-comments-o", "帮助中心", "", null, "", "", 21L, 200L));
		addInit(new SysMenu(25L, "fa fa-fw fa-map-signs", "分类设置", "/helpcenter/category/search", null,
				"/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/helpcenter/simple/category-search.html", "helpcenterCategorySearchCtrl", 24L, 100L));
		addInit(new SysMenu(26L, "fa fa-fw fa-question", "问题管理", "/helpcenter/question/search", null,
				"/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/helpcenter/simple/question-search.html", "helpcenterQuestionSearchCtrl", 24L, 200L));
		addInit(new SysMenu(27L, "fa fa-fw fa-map-signs", "分类编辑", "/helpcenter/category/update", null,
				"/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/helpcenter/simple/category-update.html", "helpcenterCategoryUpdateCtrl", 24L, 100L, YesNo.NO.key()));
		addInit(new SysMenu(28L, "fa fa-fw fa-map-signs", "问题编辑", "/helpcenter/question/update", null,
				"/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/helpcenter/simple/question-update.html", "helpcenterQuestionUpdateCtrl", 24L, 100L, YesNo.NO.key()));

	}

	public SysMenu(Long id, String icon, String title, String link, String url_pattern, String templateUrl,
			String ctrl, Long parent_id, Long sort_index) {
		super(id, icon, title, link, url_pattern, templateUrl, ctrl, parent_id, sort_index, YesNo.YES.key());
	}

	public SysMenu(Long id, String icon, String title, String link, String url_pattern, String templateUrl,
			String ctrl, Long parent_id, Long sort_index, String is_menu) {
		super(id, icon, title, link, url_pattern, templateUrl, ctrl, parent_id, sort_index, is_menu);
	}

	public SysMenu() {

	}
}
