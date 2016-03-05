package cn.cerestech.console.entity;

import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.framework.persistence.enums.ColumnDataType;

@SuppressWarnings("serial")
@Table(value = "$$employee_action_log")
public class EmployeeActionLog extends BaseEntity {

	@Column(title = "员工ID")
	public Long employee_id;

	@Column(title = "员工姓名")
	public String employee_name;

	@Column(title = "行为ID")
	public String action_key;

	@Column(title = "行为描述")
	public String action_desc;

	@Column(title = "目标ID")
	public String target_key;

	@Column(title = "目标描述")
	public String target_desc;

	@Column(type=ColumnDataType.TEXT, title = "目标内容")
	public String target_value;

	public Long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getAction_key() {
		return action_key;
	}

	public void setAction_key(String action_key) {
		this.action_key = action_key;
	}

	public String getAction_desc() {
		return action_desc;
	}

	public void setAction_desc(String action_desc) {
		this.action_desc = action_desc;
	}

	public String getTarget_key() {
		return target_key;
	}

	public void setTarget_key(String target_key) {
		this.target_key = target_key;
	}

	public String getTarget_desc() {
		return target_desc;
	}

	public void setTarget_desc(String target_desc) {
		this.target_desc = target_desc;
	}

	public String getTarget_value() {
		return target_value;
	}

	public void setTarget_value(String target_value) {
		this.target_value = target_value;
	}
}
