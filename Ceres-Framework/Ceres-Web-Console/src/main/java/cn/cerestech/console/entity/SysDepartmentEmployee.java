package cn.cerestech.console.entity;

import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;

@SuppressWarnings("serial")
@Table(value = "$$sys_employee_department")
public class SysDepartmentEmployee extends BaseEntity {

	@Column(title = "员工ID")
	public Long employee_id;

	@Column(title = "部门ID")
	public Long department_id;

	public Long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}

	public Long getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Long department_id) {
		this.department_id = department_id;
	}

	

}
