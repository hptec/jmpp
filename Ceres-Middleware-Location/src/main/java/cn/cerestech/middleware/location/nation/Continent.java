package cn.cerestech.middleware.location.nation;

import cn.cerestech.framework.core.enums.DescribableEnum;

/**
 * 行政区划的五级等级
 * 
 * @author Harry He
 * 
 */
public enum Continent implements DescribableEnum {
	AFRICA("AFRICA", "非洲"), //
	ANTARCTICA("ANTARCTICA", "南极洲"), //
	ASIA("ASIA", "亚洲"), //
	EUROPE("EUROPE", "欧洲"), //
	NORTHAMERICA("NORTHAMERICA", "北美洲"), //
	OCEANIA("OCEANIA", "大洋洲"), //
	SOUTHAMERICA("SOUTHAMERICA", "南极洲");

	private String key, desc;

	private Continent(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String desc() {
		return desc;
	}

	@Override
	public String key() {
		return key;
	}

}
