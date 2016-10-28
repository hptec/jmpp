package cn.cerestech.middleware.location.enums;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.enums.DescribableEnum;

/**
 * 行政区划的五级等级
 * 
 * @author Harry He
 * 
 */
public enum AdminLevel implements DescribableEnum {
	PROVINCE("PROVINCE", "省"), //
	CITY("CITY", "市"), //
	COUNTY("COUNTY", "区/县"), //
	TOWN("TOWN", "乡/镇"), //
	VILLAGE("VILLAGE", "村"),//
	;
	private String key, desc;

	private AdminLevel(String key, String desc) {
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

	/**
	 * 从一个完整的代码字符串里面截取符合自己级别的代码段
	 * 
	 * @param code
	 * @return
	 */
	public String extract(String code) {
		if (Strings.isNullOrEmpty(code)) {
			return null;
		}

		switch (this) {
		case PROVINCE:
			return code.length() >= 2 ? code.substring(0, 2) : null;
		case CITY:
			return code.length() >= 4 ? code.substring(0, 4) : null;
		case COUNTY:
			return code.length() >= 6 ? code.substring(0, 6) : null;
		case TOWN:
			return code.length() >= 9 ? code.substring(0, 9) : null;
		case VILLAGE:
			return code.length() >= 12 ? code.substring(0, 12) : null;
		default:
			return null;
		}

	}

	public static AdminLevel codeOf(String code) {
		if (Strings.isNullOrEmpty(code)) {
			return null;
		} else {
			int len = code.length();
			switch (len) {
			case 2:
				return PROVINCE;
			case 4:
				return CITY;
			case 6:
				return COUNTY;
			case 9:
				return TOWN;
			case 12:
				return VILLAGE;
			default:
				return null;
			}
		}

	}

}
