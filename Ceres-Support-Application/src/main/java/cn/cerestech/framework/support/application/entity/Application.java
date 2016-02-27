package cn.cerestech.framework.support.application.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.support.persistence.IdEntity;

@Entity
@Table(name = "$$sys_app")
public class Application extends IdEntity {

	private String appId;

	private String appSecret;

	private String appCopyright;

	private String developCompanyName;

	private String developCompanyWebsite;

	private String developCompanyCopyright;

	private String appFullName;

	private String appShortName;

	private String domainHost;

	private String logoUri;

	private String frontendTitle;

	private String seoKeyword;

	private String seoDesc;

	private String icpNo;

	private String serveQQ;

	private String servePhone;

	private String outResPath;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getAppCopyright() {
		return appCopyright;
	}

	public void setAppCopyright(String appCopyright) {
		this.appCopyright = appCopyright;
	}

	public String getDevelopCompanyName() {
		return developCompanyName;
	}

	public void setDevelopCompanyName(String developCompanyName) {
		this.developCompanyName = developCompanyName;
	}

	public String getDevelopCompanyWebsite() {
		return developCompanyWebsite;
	}

	public void setDevelopCompanyWebsite(String developCompanyWebsite) {
		this.developCompanyWebsite = developCompanyWebsite;
	}

	public String getDevelopCompanyCopyright() {
		return developCompanyCopyright;
	}

	public void setDevelopCompanyCopyright(String developCompanyCopyright) {
		this.developCompanyCopyright = developCompanyCopyright;
	}

	public String getAppFullName() {
		return appFullName;
	}

	public void setAppFullName(String appFullName) {
		this.appFullName = appFullName;
	}

	public String getAppShortName() {
		return appShortName;
	}

	public void setAppShortName(String appShortName) {
		this.appShortName = appShortName;
	}

	public String getDomainHost() {
		return domainHost;
	}

	public void setDomainHost(String domainHost) {
		this.domainHost = domainHost;
	}

	public String getLogoUri() {
		return logoUri;
	}

	public void setLogoUri(String logoUri) {
		this.logoUri = logoUri;
	}

	public String getFrontendTitle() {
		return frontendTitle;
	}

	public void setFrontendTitle(String frontendTitle) {
		this.frontendTitle = frontendTitle;
	}

	public String getSeoKeyword() {
		return seoKeyword;
	}

	public void setSeoKeyword(String seoKeyword) {
		this.seoKeyword = seoKeyword;
	}

	public String getSeoDesc() {
		return seoDesc;
	}

	public void setSeoDesc(String seoDesc) {
		this.seoDesc = seoDesc;
	}

	public String getIcpNo() {
		return icpNo;
	}

	public void setIcpNo(String icpNo) {
		this.icpNo = icpNo;
	}

	public String getServeQQ() {
		return serveQQ;
	}

	public void setServeQQ(String serveQQ) {
		this.serveQQ = serveQQ;
	}

	public String getServePhone() {
		return servePhone;
	}

	public void setServePhone(String servePhone) {
		this.servePhone = servePhone;
	}

	public String getOutResPath() {
		return outResPath;
	}

	public void setOutResPath(String outResPath) {
		this.outResPath = outResPath;
	}

}
