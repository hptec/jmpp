package cn.cerestech.framework.support.starter.console.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cn.cerestech.framework.support.employee.entity.Employee;
import cn.cerestech.framework.support.persistence.entity.IdEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "$$sys_privilege")
public class SysPrivilege extends IdEntity {

	private String platform;// 所属的Platform

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	private Employee employee;// 权限所有人

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "$$sys_privilege_roles", joinColumns = {
			@JoinColumn(name = "role_id", referencedColumnName = "id") })
	private List<SysRole> roles;

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

}
