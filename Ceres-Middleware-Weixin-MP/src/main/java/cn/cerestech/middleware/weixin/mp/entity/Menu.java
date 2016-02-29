package cn.cerestech.middleware.weixin.mp.entity;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.json.Jsonable;
import cn.cerestech.middleware.weixin.mp.enums.MenuType;

public class Menu implements Jsonable {
	private List<Menu> button;
	private String name;
	private String type;
	private String url;
	private List<Menu> sub_button;
	private String key;
	private String media_id;
	private Boolean isTop = Boolean.FALSE;

	public Menu addClick(String name, String key) {
		Menu m = new Menu();
		m.setType(MenuType.CLICK.key());
		m.setKey(key);
		m.setName(name);
		if (isTop) {
			addButton(m);
		} else {
			addSub(m);
		}
		return m;
	}

	public Menu add(String name) {
		Menu m = new Menu();
		m.setName(name);
		if (isTop) {
			addButton(m);
		} else {
			addSub(m);
		}
		return m;
	}

	public Menu addView(String name, String url) {
		Menu m = new Menu();
		m.setType(MenuType.VIEW.key());
		m.setUrl(url);
		m.setName(name);
		if (isTop) {
			addButton(m);
		} else {
			addSub(m);
		}
		return m;
	}

	public static Menu create(String type, String name, String key, String url, String media_id) {
		Menu m = new Menu();
		m.setType(type);
		m.setName(name);
		m.setKey(StringUtils.isBlank(key) ? null : key);
		m.setUrl(StringUtils.isBlank(url) ? null : url);
		m.setMedia_id(StringUtils.isBlank(media_id) ? null : media_id);
		return m;
	}

	public static Menu top() {
		Menu m = new Menu();
		m.isTop = Boolean.TRUE;
		return m;
	}

	public void addSub(Menu m) {
		if (this.sub_button == null) {
			this.sub_button = Lists.newArrayList();
		}
		this.sub_button.add(m);
	}

	public void addButton(Menu m) {
		if (this.button == null) {
			this.button = Lists.newArrayList();
		}
		this.button.add(m);
	}

	public List<Menu> getButton() {
		return button;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Menu> getSub_button() {
		return sub_button;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
}
