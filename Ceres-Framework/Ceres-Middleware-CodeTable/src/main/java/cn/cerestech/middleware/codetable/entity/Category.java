package cn.cerestech.middleware.codetable.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.framework.support.persistence.entity.HashTag;

/**
 * 字典表分类表
 */
@Entity
@Table(name = "$$sys_codetable_category")
public class Category extends IdEntity {

	// 该类别的拥有者
	@Embedded
	private Owner owner;

	// 分类键值
	private String category;

	// 键值个数
	private Integer count = 0;
	// 分类描述
	@Column(name = "[desc]", length = 255)
	private String desc;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy("sortIndex,id")
	private List<Code> codes;

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Code> getCodes() {
		return codes;
	}

	public void setCodes(List<Code> codes) {
		this.codes = codes;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 重新计算字典的个数
	 */
	public void reCount() {
		setCount(codes == null ? 0 : codes.size());
	}

}
