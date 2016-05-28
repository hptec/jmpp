package cn.cerestech.framework.platform.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class Developer implements Serializable {
	private String copyright;

	private String name;

	private String website;

	public String getCopyright() {
		return copyright;
	}

	public Developer setCopyright(String copyright) {
		this.copyright = copyright;
		return this;
	}

	public String getName() {
		return name;
	}

	public Developer setName(String name) {
		this.name = name;
		return this;
	}

	public String getWebsite() {
		return website;
	}

	public Developer setWebsite(String website) {
		this.website = website;
		return this;
	}

}
