package cn.cerestech.middleware.location.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

/**
 * 8个方位
 * 
 * @author Harry He
 * 
 */
public enum Direction8 implements DescribableEnum {
	EAST("EAST", "东"), //
	SOUTHEAST("SOUTHEAST", "东南"), //
	SOUTH("SOUTH", "南"), //
	SOUTHWEST("SOUTHWEST", "西南"), //
	WEST("WEST", "西"), //
	NORTHWEST("NORTHWEST", "西北"), //
	NORTH("NORTH", "北"), //
	NORTHEAST("NORTHEAST", "东北"),//
	;
	private String key, desc;

	private Direction8(String key, String desc) {
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
