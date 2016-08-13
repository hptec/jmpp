package cn.cerestech.middleware.codetable.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.cerestech.framework.core.json.JsonIgnore;
import cn.cerestech.framework.support.persistence.entity.IdEntity;
import cn.cerestech.framework.support.persistence.entity.Sortable;

/**
 * 字典表分类表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "$$sys_codetable_code")
public class Code extends IdEntity implements Sortable {

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "category_id")
	@JsonIgnore
	private Category category;
	@Column(name = "[value]", length = 1024)
	private String value;
	@Column(name = "[desc]", length = 255)
	private String desc;

	private Integer sortIndex;

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	@Override
	public Integer getSortIndex() {
		return sortIndex;
	}

}
