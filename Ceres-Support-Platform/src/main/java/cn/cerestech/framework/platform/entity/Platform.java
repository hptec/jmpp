package cn.cerestech.framework.platform.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.cerestech.framework.support.persistence.IdEntity;

@Entity
@Table(name = "$$sys_platform")
public class Platform extends IdEntity {

	private String developerCopyright;

	private String developerName;

	private String developerWebsite;

	private String frontendCopyright;

	private String frontendDomain;

	private String frontendFullName;

	private String frontendIcp;

	private String frontendLogoUri;

	private String frontendShortName;

	private String frontendSlogan;

	private String seoDesc;

	private String seoKeyword;

	private String servicePhone;

	private String serviceQQ;

	private String platformKey;

	private String platformSecret;

	private String platformAuthCode;

	@Temporal(TemporalType.TIMESTAMP)
	private Date platformExpired;

	public String getDeveloperCopyright() {
		return developerCopyright;
	}

	public String getDeveloperName() {
		return developerName;
	}

	public String getDeveloperWebsite() {
		return developerWebsite;
	}

	public String getFrontendCopyright() {
		return frontendCopyright;
	}

	public String getFrontendDomain() {
		return frontendDomain;
	}

	public String getFrontendFullName() {
		return frontendFullName;
	}

	public String getFrontendIcp() {
		return frontendIcp;
	}

	public String getFrontendLogoUri() {
		return frontendLogoUri;
	}

	public String getFrontendShortName() {
		return frontendShortName;
	}

	public String getFrontendSlogan() {
		return frontendSlogan;
	}

	public String getSeoDesc() {
		return seoDesc;
	}

	public String getSeoKeyword() {
		return seoKeyword;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public String getServiceQQ() {
		return serviceQQ;
	}

	public void setDeveloperCopyright(String developerCopyright) {
		this.developerCopyright = developerCopyright;
	}

	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}

	public void setDeveloperWebsite(String developerWebsite) {
		this.developerWebsite = developerWebsite;
	}

	public void setFrontendCopyright(String frontendCopyright) {
		this.frontendCopyright = frontendCopyright;
	}

	public void setFrontendDomain(String frontendDomain) {
		this.frontendDomain = frontendDomain;
	}

	public void setFrontendFullName(String frontendFullName) {
		this.frontendFullName = frontendFullName;
	}

	public void setFrontendIcp(String frontendIcp) {
		this.frontendIcp = frontendIcp;
	}

	public void setFrontendLogoUri(String frontendLogoUri) {
		this.frontendLogoUri = frontendLogoUri;
	}

	public void setFrontendShortName(String frontendShortName) {
		this.frontendShortName = frontendShortName;
	}

	public void setFrontendSlogan(String frontendSlogan) {
		this.frontendSlogan = frontendSlogan;
	}

	public void setSeoDesc(String seoDesc) {
		this.seoDesc = seoDesc;
	}

	public void setSeoKeyword(String seoKeyword) {
		this.seoKeyword = seoKeyword;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public void setServiceQQ(String serviceQQ) {
		this.serviceQQ = serviceQQ;
	}

	public String getPlatformKey() {
		return platformKey;
	}

	public void setPlatformKey(String platformKey) {
		this.platformKey = platformKey;
	}

	public String getPlatformSecret() {
		return platformSecret;
	}

	public void setPlatformSecret(String platformSecret) {
		this.platformSecret = platformSecret;
	}

	public Date getPlatformExpired() {
		return platformExpired;
	}

	public void setPlatformExpired(Date platformExpired) {
		this.platformExpired = platformExpired;
	}

	public String getPlatformAuthCode() {
		return platformAuthCode;
	}

	public void setPlatformAuthCode(String platformAuthCode) {
		this.platformAuthCode = platformAuthCode;
	}

}
