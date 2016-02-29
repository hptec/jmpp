package cn.cerestech.framework.support.login.service;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.entity.LoginEntity;

public interface LoginService {

	Result<LoginEntity> login(LoginEntity entity);
}
