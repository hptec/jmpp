package cn.cerestech.middleware.balance.entity;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.persistence.annotation.Table;

@SuppressWarnings("serial")
@Table("$$sys_menu")
public class SysMenu extends cn.cerestech.console.entity.SysMenu {

	static {
		addInit(new SysMenu(32L, "fa fa-fw fa-credit-card", "资金管理", "", "", "", "", -1L, 1100L));
		addInit(new SysMenu(33L, "fa fa-fw fa-cogs", "选项设置", "/balance/config", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/balance/config.html", "balanceConfigCtrl", 32L, 100L));
		addInit(new SysMenu(34L, "fa fa-fw fa-flash", "充值记录", "", "", "", "", 32L, 100L));
		addInit(new SysMenu(35L, "fa fa-fw fa-soundcloud", "提现记录", "/balance/withdraw/search", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/balance/withdraw-search.html", "balanceWithdrawSearchCtrl", 32L, 200L));
		addInit(new SysMenu(36L, "fa fa-fw fa-soundcloud", "提现详情", "/balance/withdraw/view", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/balance/withdraw-view.html", "balanceWithdrawViewCtrl", 32L, 200L, YesNo.NO.key()));
		// addInit(new SysMenu(36L, "fa fa-fw fa-credit-card", "银行卡", "", "",
		// "", "", 32L, 300L));
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
