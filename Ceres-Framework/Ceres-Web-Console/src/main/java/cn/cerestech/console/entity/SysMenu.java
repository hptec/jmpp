package cn.cerestech.console.entity;

import java.util.List;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.framework.persistence.enums.ColumnDataType;

@SuppressWarnings("serial")
@Table("$$sys_menu")
public class SysMenu extends BaseEntity {

	static {
		addInit(new SysMenu(5L, "fa fa-fw fa-user", "员工管理", "", "", "", "", -1L, 99700L));
		addInit(new SysMenu(6L, "fa fa-fw fa-users", "员工名录 ", "/employee/search", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/templates/employee/search.html", "employeeSearchCtrl",
				5L, 100L));
		addInit(new SysMenu(19L, "fa fa-fw fa-sitemap", "部门设置", "/department", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/templates/department/search.html", "departmentCtrl",
				5L, 200L));
		addInit(new SysMenu(20L, "fa fa-fw fa-cubes", "岗位设置", "/position", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/templates/position/search.html", "positionCtrl", 5L,
				300L));
		addInit(new SysMenu(16L, "fa fa-fw fa-circle-o", "编辑员工", "/employee/profile", "/employee/profile",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/templates/employee/profile.html",
				"employeeModiProfileCtrl", 5L, 100L, YesNo.NO.key()));
		// addInit(new SysMenu(8L, "fa fa-fw fa-lock", "授权管理", "", "", "", "",
		// 5L, 200L));
		addInit(new SysMenu(9L, "fa fa-fw fa-users", "角色设置", "/enterprize/authorize/rolelist", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/templates/enterprize/authorize/role_search.html",
				"roleSearchCtrl", 5L, 400L));
		addInit(new SysMenu(18L, "fa fa-fw fa-users", "角色编辑", "/enterprize/authorize/role/edit", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/templates/enterprize/authorize/role_detail.html",
				"roleDetailCtrl", 5L, 600L, YesNo.NO.key()));
		addInit(new SysMenu(10L, "fa fa-fw fa-file-text-o", "菜单设置", "/enterprize/authorize/menulist", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/templates/enterprize/authorize/menu_list.html",
				"menuListCtrl", 5L, 400L));
		addInit(new SysMenu(17L, "fa fa-fw fa-circle-o", "菜单编辑", "/enterprize/authorize/menu/:id",
				"/enterprize/authorize/menu/:id",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/templates/enterprize/authorize/menu_detail.html",
				"menuDetailCtrl", 5L, 100L, YesNo.NO.key()));
		// addInit(new SysMenu(7L, "fa fa-fw fa-circle-o", "运行状态", "", "", "",
		// "", -1L, 99900L));
		addInit(new SysMenu(1L, "fa fa-fw fa-cogs", "系统设置", "", "", "", "", -1L, 99999L));
		addInit(new SysMenu(2L, "fa fa-fw fa-sitemap", "参数配置", "/sys/config", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/templates/sys/config.html", "sysConfigCtrl", 1L, 100L));
		addInit(new SysMenu(3L, "fa fa-fw fa-sitemap", "站点设置", "/sys/site", "",
				"/$$ceres_sys/console/base/res/cp?id=/console/res/templates/sys/site.html", "sysSiteCtrl", 1L, 200L));
		
		addInit(new SysMenu(4L, "fa fa-fw fa-credit-card", "支付通道", "", "", "", "", 1L, 300L));
	}

	@Column(title = "图标")
	public String icon;
	@Column(title = "标题")
	public String title;
	@Column(title = "链接", length = 255)
	public String link;
	@Column(title = "模式匹配", length = 255)
	public String url_pattern;
	@Column(title = "模板", length = 1024)
	public String templateUrl;
	@Column(title = "控制器", length = 255)
	public String ctrl;
	@Column(title = "父ID")
	public Long parent_id;
	@Column(precision = 10, title = "排序")
	public Long sort_index;
	@Column(type = ColumnDataType.CHAR, title = "是否菜单")
	public String is_menu;

	private List<SysMenu> submenu = Lists.newArrayList();

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

	public Boolean isMenu() {
		if (Strings.isNullOrEmpty(is_menu)) {
			return Boolean.TRUE;// 默认是菜单
		}
		return YesNo.YES.key().equalsIgnoreCase(is_menu);
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCtrl() {
		return ctrl;
	}

	public String getTemplateUrl() {
		return templateUrl;
	}

	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}

	public void setCtrl(String ctrl) {
		this.ctrl = ctrl;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl_pattern() {
		return url_pattern;
	}

	public void setUrl_pattern(String url_pattern) {
		this.url_pattern = url_pattern;
	}

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public Long getSort_index() {
		return sort_index;
	}

	public void setSort_index(Long sort_index) {
		this.sort_index = sort_index;
	}

	public List<SysMenu> getSubmenu() {
		return submenu;
	}

	public void setSubmenu(List<SysMenu> submenu) {
		this.submenu = submenu;
	}

	public void addSub(SysMenu m) {
		this.submenu.add(m);
	}

	public String getIs_menu() {
		return is_menu;
	}

	public void setIs_menu(String is_menu) {
		this.is_menu = is_menu;
	}

}
