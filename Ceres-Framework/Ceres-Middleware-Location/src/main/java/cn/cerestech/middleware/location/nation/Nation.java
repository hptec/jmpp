package cn.cerestech.middleware.location.nation;

import java.io.IOException;
import java.util.Locale;

import com.google.common.base.Strings;
import com.google.common.io.Resources;

import cn.cerestech.framework.core.enums.DescribableEnum;

/**
 * 行政区划的五级等级
 * 
 * @author Harry He
 * 
 */

public enum Nation implements DescribableEnum {
	CANADA("CANADA", "加拿大", Continent.NORTHAMERICA, Locale.CANADA, "flags/canada_xs.png", "Canada"), //
	CHINA("CHINA", "中国", Continent.ASIA, Locale.CHINA, "flags/china_xs.png", "China"),//
	;

	private String key, desc;
	private Continent continent;
	private Locale locale;
	private String flag;
	private String englishName;

	private Nation(String key, String desc, Continent continent, Locale locale, String flag, String englishName) {
		this.key = key;
		this.desc = desc;
		this.continent = continent;
		this.locale = locale;
		this.flag = flag;
		this.englishName = englishName;
	}

	@Override
	public String desc() {
		return desc;
	}

	@Override
	public String key() {
		return key;
	}

	public Continent continent() {
		return continent;
	}

	public Locale locale() {
		return locale;
	}

	public String flag() {
		return flag;
	}

	public byte[] flagSource() {
		byte[] buf = new byte[0];

		if (!Strings.isNullOrEmpty(flag)) {
			try {
				buf = Resources.toByteArray(Resources.getResource(flag));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return buf;
	}

	public String englishName() {
		return englishName;
	}

}
