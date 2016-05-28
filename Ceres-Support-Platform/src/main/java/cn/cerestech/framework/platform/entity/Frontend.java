package cn.cerestech.framework.platform.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class Frontend implements Serializable {
	private String copyright;

	private String domain;

	private String fullName;

	private String icp;

	private String logoUri;

	private String shortName;

	private String slogan;

	public String getCopyright() {
		return copyright;
	}

	public Frontend setCopyright(String copyright) {
		this.copyright = copyright;
		return this;
	}

	public String getDomain() {
		return domain;
	}

	public Frontend setDomain(String domain) {
		this.domain = domain;
		return this;
	}

	public String getFullName() {
		return fullName;
	}

	public Frontend setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public String getIcp() {
		return icp;
	}

	public Frontend setIcp(String icp) {
		this.icp = icp;
		return this;
	}

	public String getLogoUri() {
		return logoUri;
	}

	public Frontend setLogoUri(String logoUri) {
		this.logoUri = logoUri;
		return this;
	}

	public String getShortName() {
		return shortName;
	}

	public Frontend setShortName(String shortName) {
		this.shortName = shortName;
		return this;
	}

	public String getSlogan() {
		return slogan;
	}

	public Frontend setSlogan(String slogan) {
		this.slogan = slogan;
		return this;
	}

}
