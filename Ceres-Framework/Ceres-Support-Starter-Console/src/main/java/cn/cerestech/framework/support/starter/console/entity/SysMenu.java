package cn.cerestech.framework.support.starter.console.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.common.collect.Lists;

import cn.cerestech.framework.support.persistence.entity.IdEntity;
import cn.cerestech.framework.support.persistence.entity.Sortable;

@SuppressWarnings("serial")
@Entity
@Table(name = "$$sys_menu")
public class SysMenu extends IdEntity implements Sortable {

	@Column(length = 35)
	private String uuid;
	private String icon;
	private String caption;
	private String uri;
	private Integer sortIndex;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent", referencedColumnName = "uuid")
	@Column(length = 35)
	private SysMenu parent;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parent")
	@Fetch(FetchMode.SELECT)
	private List<SysMenu> submenus = Lists.newArrayList();

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public SysMenu getParent() {
		return parent;
	}

	public void setParent(SysMenu parent) {
		this.parent = parent;
	}

	public List<SysMenu> getSubmenus() {
		return submenus;
	}

	public void setSubmenus(List<SysMenu> submenus) {
		this.submenus = submenus;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

}
