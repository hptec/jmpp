package cn.cerestech.console.entity;

import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;

@SuppressWarnings("serial")
@Table("$$sys_employee_role")
public class SysEmployeeRole extends BaseEntity {
	static {
		addInit(new SysEmployeeRole(1L, 1L, 1L));
	}

	@Column(title = "员工ID")
	public Long employee_id;

	@Column(title = "角色ID")
	public Long role_id;

	public SysEmployeeRole() {
		super();
	}

	public SysEmployeeRole(Long id, Long employee_id, Long role_id) {
		super();
		this.id = id;
		this.employee_id = employee_id;
		this.role_id = role_id;
	}

	public SysEmployeeRole(Long employee_id, Long role_id) {
		super();
		this.employee_id = employee_id;
		this.role_id = role_id;
	}

	public Long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}

	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

}
