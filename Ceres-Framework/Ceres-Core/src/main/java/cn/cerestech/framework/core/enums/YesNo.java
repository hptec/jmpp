package cn.cerestech.framework.core.enums;

public enum YesNo implements DescribableEnum {
	YES("Y", "是"), NO("N", "否");
	private String key, desc;

	private YesNo(String key, String desc) {
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
	
	public static YesNo keyOf(String key){
		for (YesNo item : YesNo.values()) {
			if(item.key().equalsIgnoreCase(key)){
				return item;
			}
		}
		return null;
	}
}
