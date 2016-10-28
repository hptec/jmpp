package cn.cerestech.middleware.location.entity;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.middleware.location.Divisions;
import cn.cerestech.middleware.location.enums.AdminLevel;

@SuppressWarnings("serial")
@Embeddable
public class Division implements Serializable {

	private String code;
	private String name;

	@Enumerated(EnumType.STRING)
	private AdminLevel level;

	public Division(){
	}
	
	public Division(String name,String code,AdminLevel level){
		this.name = name;
		this.code = code;
		this.level = level;
	}
	
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

	/**
	 * 在此层级关系上向上寻找特定级别的,没有找到则返回空。
	 * 
	 * @param level
	 * @return
	 */
	public Division findByLevel(AdminLevel level) {
		if (level == null) {
			return null;
		}
		if (level.ordinal() > this.level.ordinal()) {
			// 要求级别低于现在级别，返回null
			return null;
		}
		if (level.equals(this.level)) {
			return Divisions.findByCode(this.getCode());
		}
		String code = null;
		switch (level) {
		case PROVINCE:
			code = AdminLevel.PROVINCE.extract(this.getCode());
			break;
		case CITY:
			code = AdminLevel.CITY.extract(this.getCode());
			break;
		case COUNTY:
			code = AdminLevel.COUNTY.extract(this.getCode());
			break;
		case TOWN:
			code = AdminLevel.TOWN.extract(this.getCode());
			break;
		case VILLAGE:
			code = AdminLevel.VILLAGE.extract(this.getCode());
			break;
		}

		return code == null ? null : Divisions.findByCode(code);

	}

	public Boolean isEmpty() {
		if (level == null || Strings.isNullOrEmpty(code) || Strings.isNullOrEmpty(name)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	public static void main(String[] args) throws Throwable {
		Jsons jsonData = Jsons
				.from(Resources.toString(Resources.getResource("divisions_zh-CN2.json"), Charset.defaultCharset()));

		jsonData.asList().forEach(p -> {
			String pCode = p.get("code").asString();
			p.get("children").asList().forEach(c -> {
				String cCode = c.get("code").asString();
				c.get("children").asList().forEach(o -> {
					String oCode = o.get("code").asString();
					o.getRoot().getAsJsonObject().addProperty("code", pCode + cCode + oCode);
				});
				c.getRoot().getAsJsonObject().addProperty("code", pCode + cCode);
			});
		});

		Files.write(jsonData.toPrettyJson().getBytes(), new File("divisions_zh-CN.json"));
	}

}
