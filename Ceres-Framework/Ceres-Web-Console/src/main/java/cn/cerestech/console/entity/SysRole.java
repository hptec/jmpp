package cn.cerestech.console.entity;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.framework.persistence.enums.ColumnDataType;

@SuppressWarnings("serial")
@Table("$$sys_role")
public class SysRole extends BaseEntity {
	static {
		addInit(new SysRole(1L, "超级管理员", YesNo.YES.key()));
	}

	@Column(title = "名称")
	public String name;

	@Column(title = "员工数")
	public Integer employee_count = 0;

	@Column(title = "菜单数", defaultValue = "0")
	public Integer menu_count = 0;

	public List<Long> employeeIds = Lists.newArrayList();

	public List<Long> menuIds = Lists.newArrayList();
	/**
	 * 超级管理员拥有所有的菜单
	 */
	@Column(type = ColumnDataType.CHAR, title = "是否超级管理员")
	public String super_admin = YesNo.NO.key();

	public SysRole() {
		super();
	}

	public SysRole(Long id, String name, String super_admin) {
		super();
		setId(id);
		this.name = name;
		this.create_time = new Date();
		this.super_admin = super_admin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuper_admin() {
		return super_admin;
	}

	public void setSuper_admin(String super_admin) {
		this.super_admin = super_admin;
	}

	public Boolean isSuperAdmin() {
		if (super_admin == null) {
			return Boolean.FALSE;
		} else {
			return YesNo.YES.key().equalsIgnoreCase(super_admin);
		}
	}

	public Integer getEmployee_count() {
		return employee_count;
	}

	public void setEmployee_count(Integer employee_count) {
		this.employee_count = employee_count;
	}

	public Integer getMenu_count() {
		return menu_count;
	}

	public void setMenu_count(Integer menu_count) {
		this.menu_count = menu_count;
	}

	public List<Long> getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(List<Long> employeeIds) {
		this.employeeIds = employeeIds;
	}

	public List<Long> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<Long> menuIds) {
		this.menuIds = menuIds;
	}

}
