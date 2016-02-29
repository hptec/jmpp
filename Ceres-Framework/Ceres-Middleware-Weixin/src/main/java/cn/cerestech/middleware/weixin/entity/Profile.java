package cn.cerestech.middleware.weixin.entity;

import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;

/**
 * 微信用户基本信息
 * 
 * @author bird
 *
 */
@SuppressWarnings("serial")
@Table("$$weixin_profile")
public class Profile extends BaseEntity {
	@Column(title = "APPID")
	public String appid;
	@Column(title = "密钥", length = 200)
	public String secret;
	@Column(title = "配置名称", length = 254)
	public String profile_name;

	@Column(title = "网关Url", length = 500)
	public String gatewayUrl;

	@Column(title = "网关密钥", length = 254)
	public String gatewayToken;

	@Column(title = "JS域名", length = 500)
	public String jsDomain;

	@Column(title = "业务域名", length = 500)
	public String target_host;

	@Column(title = "是否默认", length = 1)
	public String is_default;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getProfile_name() {
		return profile_name;
	}

	public void setProfile_name(String profile_name) {
		this.profile_name = profile_name;
	}

	public String getIs_default() {
		return is_default;
	}

	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}

	public String getTarget_host() {
		return target_host;
	}

	public void setTarget_host(String target_host) {
		this.target_host = target_host;
	}

	public String getGatewayToken() {
		return gatewayToken;
	}

	public void setGatewayToken(String gatewayToken) {
		this.gatewayToken = gatewayToken;
	}

	public String getGatewayUrl() {
		return gatewayUrl;
	}

	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}

	public String getJsDomain() {
		return jsDomain;
	}

	public void setJsDomain(String jsDomain) {
		this.jsDomain = jsDomain;
	}

}
