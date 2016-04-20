package cn.cerestech.framework.support.login.provider;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.entity.LoginEntity;

public interface LoginServiceProvider<T extends LoginEntity> {

	Result<T> login(T entity, Boolean remember);

	Boolean validateRememberToken(String token, Long id);
}
