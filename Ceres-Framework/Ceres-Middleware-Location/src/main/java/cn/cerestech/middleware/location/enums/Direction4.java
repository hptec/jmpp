package cn.cerestech.middleware.location.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

/**
 * 四个方位
 * 
 * @author Harry He
 * 
 */
public enum Direction4 implements DescribableEnum {
	EAST("EAST", "东"), //
	SOUTH("SOUTH", "南"), //
	WEST("WEST", "西"), //
	NORTH("NORTH", "北"), //
	;
	private String key, desc;

	private Direction4(String key, String desc) {
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
