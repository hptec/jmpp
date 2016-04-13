package cn.cerestech.support.web.console.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.common.collect.Lists;

import cn.cerestech.framework.support.persistence.IdEntity;

@Entity
@Table(name = "$$sys_menu")
public class SysMenu extends IdEntity {

	// static {
	// addInit(new SysMenu(5L, "fa fa-fw fa-user", "员工管理", "", "", "", "", -1L,
	// 99700L));
	// addInit(new SysMenu(6L, "fa fa-fw fa-users", "员工名录 ", "/employee/search",
	// "",
	// "/$$ceres_sys/console/base/res/cp?id=/console/res/templates/employee/search.html",
	// "employeeSearchCtrl",
	// 5L, 100L));
	// addInit(new SysMenu(19L, "fa fa-fw fa-sitemap", "部门设置", "/department",
	// "",
	// "/$$ceres_sys/console/base/res/cp?id=/console/res/templates/department/search.html",
	// "departmentCtrl",
	// 5L, 200L));
	// addInit(new SysMenu(20L, "fa fa-fw fa-cubes", "岗位设置", "/position", "",
	// "/$$ceres_sys/console/base/res/cp?id=/console/res/templates/position/search.html",
	// "positionCtrl", 5L,
	// 300L));
	// addInit(new SysMenu(16L, "fa fa-fw fa-circle-o", "编辑员工",
	// "/employee/profile", "/employee/profile",
	// "/$$ceres_sys/console/base/res/cp?id=/console/res/templates/employee/profile.html",
	// "employeeModiProfileCtrl", 5L, 100L, YesNo.NO.key()));
	// // addInit(new SysMenu(8L, "fa fa-fw fa-lock", "授权管理", "", "", "", "",
	// // 5L, 200L));
	// addInit(new SysMenu(9L, "fa fa-fw fa-users", "角色设置",
	// "/enterprize/authorize/rolelist", "",
	// "/$$ceres_sys/console/base/res/cp?id=/console/res/templates/enterprize/authorize/role_search.html",
	// "roleSearchCtrl", 5L, 400L));
	// addInit(new SysMenu(18L, "fa fa-fw fa-users", "角色编辑",
	// "/enterprize/authorize/role/edit", "",
	// "/$$ceres_sys/console/base/res/cp?id=/console/res/templates/enterprize/authorize/role_detail.html",
	// "roleDetailCtrl", 5L, 600L, YesNo.NO.key()));
	// addInit(new SysMenu(10L, "fa fa-fw fa-file-text-o", "菜单设置",
	// "/enterprize/authorize/menulist", "",
	// "/$$ceres_sys/console/base/res/cp?id=/console/res/templates/enterprize/authorize/menu_list.html",
	// "menuListCtrl", 5L, 400L));
	// addInit(new SysMenu(17L, "fa fa-fw fa-circle-o", "菜单编辑",
	// "/enterprize/authorize/menu/:id",
	// "/enterprize/authorize/menu/:id",
	// "/$$ceres_sys/console/base/res/cp?id=/console/res/templates/enterprize/authorize/menu_detail.html",
	// "menuDetailCtrl", 5L, 100L, YesNo.NO.key()));
	// // addInit(new SysMenu(7L, "fa fa-fw fa-circle-o", "运行状态", "", "", "",
	// // "", -1L, 99900L));
	// addInit(new SysMenu(1L, "fa fa-fw fa-cogs", "系统设置", "", "", "", "", -1L,
	// 99999L));
	// addInit(new SysMenu(2L, "fa fa-fw fa-sitemap", "参数配置", "/sys/config", "",
	// "/$$ceres_sys/console/base/res/cp?id=/console/res/templates/sys/config.html",
	// "sysConfigCtrl", 1L,
	// 100L));
	// addInit(new SysMenu(3L, "fa fa-fw fa-sitemap", "站点设置", "/sys/site", "",
	// "/$$ceres_sys/console/base/res/cp?id=/console/res/templates/sys/site.html",
	// "sysSiteCtrl", 1L, 200L));
	//
	// addInit(new SysMenu(4L, "fa fa-fw fa-credit-card", "支付通道", "", "", "",
	// "", 1L, 300L));
	// }
	@Column(name = "[key]")
	private String key;
	private String icon;
	private String caption;
	private String parent;
	private Long sort;

	@Transient
	private List<SysMenu> submenus = Lists.newArrayList();

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public List<SysMenu> getSubmenus() {
		return submenus;
	}

	public void setSubmenus(List<SysMenu> submenus) {
		this.submenus = submenus;
	}

}
