package cn.cerestech.console.entity;

import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;

@SuppressWarnings("serial")
@Table(value = "$$sys_position")
public class SysPosition extends BaseEntity {

	@Column(title = "名称")
	public String name;

	public SysPosition() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
