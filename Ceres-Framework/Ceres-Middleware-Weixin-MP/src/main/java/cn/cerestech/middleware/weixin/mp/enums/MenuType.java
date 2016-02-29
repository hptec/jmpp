package cn.cerestech.middleware.weixin.mp.enums;

public enum MenuType {
	/**
	 * 文本信息
	 */
	CLICK("click"),
	/**
	 * 图片信息
	 */
	VIEW("view");

	private String key;

	private MenuType(String key) {
		this.key = key;
	}

	public String key() {
		return this.key;
	}

	public static MenuType keyOf(String key) {
		for (MenuType type : MenuType.values()) {
			if (type.key().equalsIgnoreCase(key)) {
				return type;
			}
		}
		return null;
	}

}
