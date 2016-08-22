package cn.cerestech.framework.support.starter.console.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.json.JsonIgnore;
import cn.cerestech.framework.support.persistence.entity.IdEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "$$sys_menu")
public class SysMenu extends IdEntity {

	private String platform;// 所属的Platform
	@Column(length = 35)
	private String uuid;// 唯一的Key
	private String icon;// 图标
	private String caption;// 显示标题
	private String uri;// 跳转链接

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent", referencedColumnName = "uuid")
	@JsonIgnore
	private SysMenu parent;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parent")
	@Fetch(FetchMode.SELECT)
	private List<SysMenu> submenus = Lists.newArrayList();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "$$sys_menu_pages")
	private List<String> privilege;

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

	public List<String> getPrivilege() {
		return privilege;
	}

	public void setPrivilege(List<String> privilege) {
		this.privilege = privilege;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

}
