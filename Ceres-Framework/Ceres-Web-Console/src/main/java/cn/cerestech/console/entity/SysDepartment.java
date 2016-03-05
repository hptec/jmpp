package cn.cerestech.console.entity;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;

@SuppressWarnings("serial")
@Table(value = "$$sys_department")
public class SysDepartment extends BaseEntity {

	@Column(title = "名称")
	public String name;

	@Column(title = "员工数")
	public Long employee_count = 0L;

	@Column(title = "上级部门")
	public Long parent_id;

	@Column(title = "排序")
	public Long sort = 0L;

	public Integer depth = 0;

	public List<SysDepartment> children = Lists.newArrayList();

	public SysDepartment() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getEmployee_count() {
		return employee_count;
	}

	public void setEmployee_count(Long employee_count) {
		this.employee_count = employee_count;
	}

	public List<SysDepartment> getChildren() {
		return children;
	}

	public void addChild(SysDepartment dep) {
		this.children.add(dep);
	}

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public void setChildren(List<SysDepartment> children) {
		this.children = children;
	}

}
