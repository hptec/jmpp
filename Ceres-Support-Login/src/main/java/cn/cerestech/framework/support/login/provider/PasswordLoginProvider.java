package cn.cerestech.framework.support.login.provider;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.login.entity.Login;
import cn.cerestech.framework.support.login.entity.Loginable;
import cn.cerestech.framework.support.login.enums.ErrorCodes;

public abstract class PasswordLoginProvider<T extends Loginable> implements LoginProvider<T> {
	public static final String LOGIN_ID = "usr";
	public static final String LOGIN_PWD = "pwd";

	@Override
	public Result<Long> validate() {
		LoginDao<T> dao = getDao();
		Login fromLogin = getLogin();
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

	public Login getLogin() {
		String usr = getRequest(LOGIN_ID);
		String pwd = getRequest(LOGIN_PWD);
		return Login.from(usr, pwd);
	}

}
