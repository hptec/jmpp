package cn.cerestech.framework.support.starter.console.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.support.persistence.entity.IdEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "$$sys_role")
public class SysRole extends IdEntity {

	private String platform;// 所属的Platform
	@Column(length = 35)
	private String uuid;
	private String name;
	private YesNo isSuperAdmin;// 是否超级管理员所属

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "$$sys_role_menu")
	private List<String> menus;

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public YesNo getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(YesNo isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public List<String> getMenus() {
		return menus;
	}

	public void setMenus(List<String> menus) {
		this.menus = menus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
