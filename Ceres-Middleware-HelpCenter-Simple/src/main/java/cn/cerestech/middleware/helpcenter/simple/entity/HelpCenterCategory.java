package cn.cerestech.middleware.helpcenter.simple.entity;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;

@SuppressWarnings("serial")
@Table(value = "$$helpcenter_category", comment = "帮助中心分类")
public class HelpCenterCategory extends BaseEntity {
	@Column(title = "名称")
	public String name;

	@Column(title = "父级")
	public Long parent_id;

	@Column(title = "排序序号")
	public Integer sort = 99999;

	@Column(title = "是否可见")
	public String visible;

	private List<HelpCenterCategory> children = Lists.newArrayList();

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HelpCenterCategory> getChildren() {
		return children;
	}

	public void setChildren(List<HelpCenterCategory> children) {
		this.children = children;
	}

	public void addChild(HelpCenterCategory cate) {
		this.children.add(cate);
	}

}
