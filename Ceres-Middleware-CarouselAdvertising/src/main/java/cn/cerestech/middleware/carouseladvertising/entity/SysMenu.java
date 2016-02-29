package cn.cerestech.middleware.carouseladvertising.entity;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.persistence.annotation.Table;

@SuppressWarnings("serial")
@Table("$$sys_menu")
public class SysMenu extends cn.cerestech.console.entity.SysMenu {

	static {
		addInit(new SysMenu(21L, "fa fa-fw fa-sitemap", "网站管理", "", "", "", "", -1L, 99600L));
		addInit(new SysMenu(29L, "fa fa-fw fa-object-group", "广告轮播", "/carouseladvertising/search", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/carouseladvertising/search.html", "", 21L, 100L));
		addInit(new SysMenu(30L, "fa fa-fw fa-edit", "广告编辑", "/carouseladvertising/update", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/carouseladvertising/update.html", "middlewareCarouselAdvertisingUpdateCtrl", 29L, 000L,YesNo.NO.key()));
		addInit(new SysMenu(31L, "fa fa-fw fa-edit", "广告配置", "/carouseladvertising/config", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/carouseladvertising/config.html", "middlewareCarouselAdvertisingConfigCtrl", 29L, 200L,YesNo.NO.key()));
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
