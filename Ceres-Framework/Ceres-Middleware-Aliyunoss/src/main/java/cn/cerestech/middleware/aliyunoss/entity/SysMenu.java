package cn.cerestech.middleware.aliyunoss.entity;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.persistence.annotation.Table;

@SuppressWarnings("serial")
@Table("$$sys_menu")
public class SysMenu extends cn.cerestech.console.entity.SysMenu {

	static {
		addInit(new SysMenu(1L, "fa fa-fw fa-cogs", "系统设置", "", "", "", "", -1L, 99999L));
		addInit(new SysMenu(11L, "fa fa-fw fa-opera", "图片分流", "", "", "", "", 1L, 400L));
		addInit(new SysMenu(14L, "fa fa-fw fa-university", "阿里云OSS", "/middleware/aliyunoss/icp", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/aliyunoss/aliyunoss_icp.html",
				"sysAliyunOssIcpCtrl", 11L, 100L));
		addInit(new SysMenu(15L, "fa fa-fw fa-navicon", "同步记录", "/middleware/aliyunoss/records", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/aliyunoss/aliyunoss_records.html",
				"sysAliyunOssRecordsCtrl", 11L, 200L));
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
