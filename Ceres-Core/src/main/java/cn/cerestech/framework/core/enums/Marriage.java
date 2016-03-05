package cn.cerestech.framework.core.enums;

public enum Marriage implements DescribableEnum {

	UNMARRIED("UNMARRIED", "未婚"), MARRIED("MARRIED", "已婚"), DEVORCE("DEVORCE", "离异");
	private String key, desc;

	private Marriage(String key, String desc) {
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
