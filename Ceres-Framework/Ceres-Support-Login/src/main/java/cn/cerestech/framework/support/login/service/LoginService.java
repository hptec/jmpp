package cn.cerestech.framework.support.login.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.core.date.Moment;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.core.utils.Random;
import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.login.entity.Login;
import cn.cerestech.framework.support.login.entity.Loginable;
import cn.cerestech.framework.support.login.enums.ErrorCodes;
import cn.cerestech.framework.support.login.interceptor.LoginInterceptor;
import cn.cerestech.framework.support.login.operator.UserSessionOperator;
import cn.cerestech.framework.support.login.provider.LoginProvider;
import cn.cerestech.framework.support.persistence.entity.Confidential;
import cn.cerestech.framework.support.web.operator.PlatformOperator;

@Service
public class LoginService<T extends Loginable> implements PlatformOperator, UserSessionOperator {

	@Autowired
	LoginInterceptor interceptor;

	@SuppressWarnings("unchecked")
	public Result<T> login(LoginProvider<T> provider) {
		if (provider == null) {
			return Result.error(ErrorCodes.LOGIN_FAILED);
		}

		Result<Long> ret = provider.validate();
		if (!ret.isSuccess()) {
			return Result.error(ret);
		}
		LoginDao<T> dao = provider.getDao();
		T t = dao.findOne(ret.getObject());
		Login inDb = t.getLogin();

		if (inDb.getFrozen().equals(YesNo.YES)) {
			return Result.error(ErrorCodes.LOGIN_FROZEN);
		}

		// 记录remember me
		Boolean needRemember = provider.getRemember();

		Date now = new Date();
		if (needRemember) {
			// 记住登录
			inDb.setRememberToken(Random.uuid());
			inDb.setRememberExpired(Moment.now().addMonth(3).toDate());
		} else {
			// 清除登录
			inDb.setRememberToken(null);
			inDb.setRememberExpired(now);
		}

		inDb.setLastLoginTime(inDb.getThisLoginTime());
		inDb.setThisLoginTime(new Date());

		Long id = t.getId();

		// 记录登录信息到cookie中
		putUserId(id);

		// 如果用户记住登录，还要放入cookie
		if (needRemember) {
			// 记录登录,添加cookie
			putRemember(id, inDb.getRememberToken());
		} else {
			// 清除cookie
			clearRemember();
		}

		// 保存登录信息
		dao.save(t);

		return Result.success(t instanceof Confidential ? ((Confidential<T>) t).safty() : t);
	}

}
