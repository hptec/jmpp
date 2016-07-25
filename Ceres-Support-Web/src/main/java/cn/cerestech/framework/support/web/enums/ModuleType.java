package cn.cerestech.framework.support.web.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum ModuleType implements DescribableEnum {
	JAVASCRIPT("jsModules", "Javascript模块"), //
	MENU("menus", "菜单"), //
	STARTER("starter", "启动器"), //
	PAGES("pages", "页面"),//
	

	;

	private String key, desc;

	ModuleType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String key() {
		return key;
	}

	@Override
	public String desc() {
		return desc;
	}

}
