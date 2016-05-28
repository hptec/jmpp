package cn.cerestech.framework.support.login.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.utils.Encrypts;

@Embeddable
public class Login {

	// 登录名
	@Column(length = 40)
	private String id;

	// 登录密码
	@Column(length = 70)
	private String pwd;

	// 登录是否冻结
	@Column(length = 70)
	private YesNo frozen = YesNo.NO;

	// 登录令牌
	private String rememberToken;
	// 令牌过期时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date rememberExpired;

	// 本次登录时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date thisLoginTime;
	// 上次登录时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRememberToken() {
		return rememberToken;
	}

	public void setRememberToken(String rememberToken) {
		this.rememberToken = rememberToken;
	}

	public Date getRememberExpired() {
		return rememberExpired;
	}

	public void setRememberExpired(Date rememberExpired) {
		this.rememberExpired = rememberExpired;
	}

	public YesNo getFrozen() {
		return frozen;
	}

	public void setFrozen(YesNo frozen) {
		this.frozen = frozen;
	}

	public Boolean isEmpty() {
		return Strings.isNullOrEmpty(id) || Strings.isNullOrEmpty(pwd);
	}

	public Boolean comparePassword(String password) {
		return Strings.nullToEmpty(pwd).equals(Encrypts.md5(password));
	}

	public Date getThisLoginTime() {
		return thisLoginTime;
	}

	public void setThisLoginTime(Date thisLoginTime) {
		this.thisLoginTime = thisLoginTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public static Login from(String loginId, String loginPwd) {
		Login login = new Login();
		login.setId(loginId);
		login.setPwd(loginPwd);
		return login;
	}
}
