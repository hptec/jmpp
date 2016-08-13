package cn.cerestech.framework.support.login.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.login.entity.Login;
import cn.cerestech.framework.support.login.entity.LoginField;
import cn.cerestech.framework.support.login.entity.Loginable;
import cn.cerestech.framework.support.login.enums.ErrorCodes;

public abstract class PasswordLoginProvider<T extends Loginable> implements LoginProvider<T> {
	public static final String LOGIN_ID = "usr";
	public static final String LOGIN_PWD = "pwd";

	@Autowired
	LoginDao<T> loginDao;

	@Override
	public Result<Long> validate() {
		Login fromLogin = getLogin();
		if (fromLogin == null || fromLogin.isEmpty()) {
			return Result.error(ErrorCodes.LOGIN_FAILED);
		}

		// 从数据库获取用户对象
		T t = getLoginFromDB(fromLogin);
		if (t == null) {
			return Result.error(ErrorCodes.LOGIN_FAILED);
		}

		Login inDb = t.getLogin();

		if (inDb == null || inDb.isEmpty()) {
			return Result.error(ErrorCodes.LOGIN_FAILED);
		}

		if (!inDb.comparePassword(fromLogin.getPwd())) {
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

	@Override
	public List<LoginField> getLoginFields() {
		List<LoginField> retList = Lists.newArrayList();
		retList.add(new LoginField("text", LOGIN_ID, "登录用户名"));
		retList.add(new LoginField("password", LOGIN_PWD, "登录密码"));
		return retList;
	}

	public T getLoginFromDB(Login fromLogin) {
		return loginDao.findUniqueByLoginIdIgnoreCase(fromLogin.getId());
	}

}
