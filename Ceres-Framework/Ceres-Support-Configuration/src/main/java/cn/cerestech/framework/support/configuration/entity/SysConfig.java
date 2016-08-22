package cn.cerestech.framework.support.configuration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.support.persistence.entity.IdEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "$$sys_config")
public class SysConfig extends IdEntity {
	@Column(name = "[key]", length = 70)
	public String key;
	@Column(name = "[value]", length = 1024)
	public String value;
	@Column(name = "[desc]", length = 255)
	public String desc;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
