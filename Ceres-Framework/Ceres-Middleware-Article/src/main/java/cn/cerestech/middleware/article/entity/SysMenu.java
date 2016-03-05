package cn.cerestech.middleware.article.entity;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.persistence.annotation.Table;

@SuppressWarnings("serial")
@Table("$$sys_menu")
public class SysMenu extends cn.cerestech.console.entity.SysMenu {

	static {
		addInit(new SysMenu(21L, "fa fa-fw fa-sitemap", "网站管理", "", "", "", "", -1L, 99600L));
		addInit(new SysMenu(22L, "fa fa-fw fa-file-text-o", "文章管理", "/article/search", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/article/search.html", "middlewareArticleSearchCtrl", 21L, 200L));
		addInit(new SysMenu(23L, "fa fa-fw fa-edit", "文章编辑", "/article/update", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/article/update.html", "middlewareArticleUpdateCtrl", 21L, 100L,YesNo.NO.key()));
	}

	public SysMenu() {
		super();
	}

	public SysMenu(Long id, String icon, String title, String link, String url_pattern, String templateUrl, String ctrl,
			Long parent_id, Long sort_index) {
		this(id, icon, title, link, url_pattern, templateUrl, ctrl, parent_id, sort_index, YesNo.YES.key());
	}

	public SysMenu(Long id, String icon, String title, String link, String url_pattern, String templateUrl, String ctrl,
			Long parent_id, Long sort_index, String is_menu) {
		super();
		this.id = id;
		this.icon = icon;
		this.title = title;
		this.link = link;
		this.url_pattern = url_pattern;
		this.templateUrl = templateUrl;
		this.ctrl = ctrl;
		this.parent_id = parent_id;
		this.sort_index = sort_index;
		this.is_menu = is_menu;
	}

}
