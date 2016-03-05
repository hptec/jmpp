package cn.cerestech.middleware.sms.entity;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.persistence.annotation.Table;

@SuppressWarnings("serial")
@Table("$$sys_menu")
public class SysMenu extends cn.cerestech.console.entity.SysMenu {

	static {

		addInit(new SysMenu(1L, "fa fa-fw fa-cogs", "系统设置", "", "", "", "", -1L, 99999L));
		addInit(new SysMenu(3L, "fa fa-fw fa-signal", "短信服务", "", "", "", "", 1L, 200L));
		addInit(new SysMenu(12L, "fa fa-fw fa-university", "服务商", "/middleware/sms/icp", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/sms/sms_icp.html", "sysSmsIcpCtrl", 3L,
				100L));
		addInit(new SysMenu(13L, "fa fa-fw fa-navicon", "发送记录", "/sys/sms/records", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/sms/sms_records.html", "sysSmsRecordsCtrl",
				3L, 200L));
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
