package cn.cerestech.middleware.location.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum GeodeticSystem implements DescribableEnum {
	WGS84("WGS84", "世界大地坐标系"), //
	GCJ02("GCJ02", "中国国家测绘局坐标"), //
	BD09("BD09", "百度经纬度坐标"),//
	;
	private String key, desc;

	private GeodeticSystem(String key, String desc) {
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
	
	public static GeodeticSystem keyOf(String key){
		for (GeodeticSystem geo : GeodeticSystem.values()) {
			if(geo.key().equalsIgnoreCase(key)){
				return geo;
			}
		}
		return null;
	}

}
