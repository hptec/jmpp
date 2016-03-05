package cn.cerestech.middleware.aliyunoss.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum Providers implements DescribableEnum {
	BEIJING("北京", "北京"), //
	HANGZHOU("杭州", "杭州"), //
	HONGKONG("香港", "香港"), //
	QINGDAO("青岛", "青岛"), //
	SHANSGHAI("上海", "上海"), //
	SHENZHEN("深圳", "深圳"), //
	SILICONVALLEY("美国硅谷", "美国硅谷"), //
	SINGAPORE("亚太新加坡", "亚太新加坡"),//
	;

	private String key, desc;

	private Providers(String key, String desc) {
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
