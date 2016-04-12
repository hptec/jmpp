package cn.cerestech.support.web.console.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.support.persistence.IdEntity;

@Entity
@Table(name = "$$sys_menu_pages")
public class SysMenuPage extends IdEntity {

	private String menuKey;
	@Column(length = 255)
	private String uri;
	@Column(length = 1024)
	private String tpl;


	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getTpl() {
		return tpl;
	}

	public void setTpl(String tpl) {
		this.tpl = tpl;
	}

}
