package cn.cerestech.framework.core.search;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum Sort implements DescribableEnum{
	ASC("ASC","升序"),
	DESC("DESC","降序");
	
	private String key, desc;
	private Sort(String key, String desc){
		this.key = key;
		this.desc = desc;
	}
	@Override
	public String desc() {
		return this.desc;
	}
	@Override
	public String key() {
		return this.key;
	}
}
