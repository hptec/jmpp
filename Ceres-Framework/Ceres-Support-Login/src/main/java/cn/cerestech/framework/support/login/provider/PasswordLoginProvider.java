package cn.cerestech.framework.support.login.provider;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.login.entity.Login;
import cn.cerestech.framework.support.login.entity.Loginable;
import cn.cerestech.framework.support.login.enums.ErrorCodes;

public class PasswordLoginProvider<T extends Loginable> implements LoginProvider<T> {

	private Login fromLogin = null;
	private LoginDao<T> dao;

	@Override
	public Result<Long> validate() {
		if (dao == null || fromLogin == null || fromLogin.isEmpty()) {
			return Result.error(ErrorCodes.LOGIN_FAILED);
		}

		// 从数据库获取用户对象
		T t = dao.findUniqueByLoginId(fromLogin.getId());
		if (t == null) {
			return Result.error(ErrorCodes.LOGIN_FAILED);
		}

		Login inDb = t.getLogin();

		if (inDb == null || inDb.isEmpty()) {
			return Result.error(ErrorCodes.LOGIN_FAILED);
		}

		if (!inDb.comparePassword(fromLogin.getId())) {
			// 比对用户名密码
			return Result.error(ErrorCodes.LOGIN_FAILED);
		}

		return Result.success(t.getId());
	}

	@Override
	public LoginDao<T> getDao() {
		return dao;
	}

	public static <T extends Loginable> LoginProvider<T> from(Login login, LoginDao<T> dao) {
		PasswordLoginProvider<T> plp = new PasswordLoginProvider<T>();
		plp.fromLogin = login;
		plp.dao = dao;

		return plp;
	}
}
