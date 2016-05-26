package cn.cerestech.middleware.location.entity;

import java.io.Serializable;

import javax.persistence.Convert;
import javax.persistence.Embeddable;

import com.google.common.base.Strings;

import cn.cerestech.middleware.location.converter.AdminLevelConverter;
import cn.cerestech.middleware.location.enums.AdminLevel;

@SuppressWarnings("serial")
@Embeddable
public class Division implements Serializable {

	private String code;
	private String name;
	@Convert(converter = AdminLevelConverter.class)
	private AdminLevel level;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AdminLevel getLevel() {
		return level;
	}

	public void setLevel(AdminLevel level) {
		this.level = level;
	}

	public Boolean isEmpty() {
		if (level == null || Strings.isNullOrEmpty(code) || Strings.isNullOrEmpty(name)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
}
