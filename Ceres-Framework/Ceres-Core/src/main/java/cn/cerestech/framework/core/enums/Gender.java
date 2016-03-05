package cn.cerestech.framework.core.enums;

public enum Gender implements DescribableEnum {

	MALE("M", "先生"), FEMALE("F", "女士"), UNKNOWN("U", "未知");
	private String key, desc;

	private Gender(String key, String desc) {
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
