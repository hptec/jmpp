package cn.cerestech.framework.support.login.entity;

import java.util.Date;

public interface LoginEntity {

	String getLoginId();

	void setLoginId(String loginId);

	String getLoginPwd();

	void setLoginPwd(String loginPwd);

	public String getRememberToken();

	public void setRememberToken(String rememberToken);

	public Date getRememberExpired();

	public void setRememberExpired(Date rememberExpired);
}
