package cn.cerestech.framework.support.login.provider;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.entity.LoginEntity;

public interface LoginServiceProvider {

	Result<LoginEntity> login(LoginEntity entity);

	Boolean validateRememberToken(String token, Long id);
}
