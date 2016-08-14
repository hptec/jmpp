package cn.cerestech.framework.support.login.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.primitives.Longs;

import cn.cerestech.framework.core.date.Moment;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.core.utils.Random;
import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.login.entity.Login;
import cn.cerestech.framework.support.login.entity.Loginable;
import cn.cerestech.framework.support.login.enums.LoginErrorCodes;
import cn.cerestech.framework.support.login.operator.UserSessionOperator;
import cn.cerestech.framework.support.login.provider.LoginProvider;
import cn.cerestech.framework.support.persistence.entity.Confidential;
import cn.cerestech.framework.support.starter.Cookies;
import cn.cerestech.framework.support.starter.dao.PlatformDao;
import cn.cerestech.framework.support.starter.entity.Platform;
import cn.cerestech.framework.support.starter.operator.PlatformOperator;

@Service
public class LoginService<T extends Loginable> implements PlatformOperator, UserSessionOperator {

	@Autowired
	LoginProvider<T> loginProvider;

	@Autowired
	LoginDao<T> loginDao;

	@Autowired
	PlatformDao platformDao;

	@SuppressWarnings("unchecked")
	public Result<T> login() {

		Result<Long> ret = loginProvider.validate();
		if (!ret.isSuccess()) {
			return Result.error(ret);
		}
		T t = loginDao.findOne(ret.getObject());
		Login inDb = t.getLogin();

		if (inDb.getFrozen().equals(YesNo.YES)) {
			return Result.error(LoginErrorCodes.LOGIN_FROZEN);
		}

		// 记录remember me
		Boolean needRemember = loginProvider.getRemember();

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
		loginDao.save(t);

		return Result.success(t instanceof Confidential ? ((Confidential<T>) t).safty() : t);
	}

	public Result<Loginable> logout() {
		clearRemember();
		putUserId(null);
		return Result.success();
	}

	public void putRemember(Long id, String token) {
		if (id != null && !Strings.isNullOrEmpty(token)) {
			Platform platform = platformDao.findOne(getPlatformId());

			String cKeyToken = COOKIE_REMEMBER_TOKEN + platform.getKey();
			String cKeyId = COOKIE_REMEMBER_ID + platform.getKey();
			Cookies cookies = Cookies.from(getRequest());
			cookies.add(cKeyToken, token);
			cookies.add(cKeyId, id.toString());
			cookies.flushTo(getResponse());
		}
	}

	public String getRememberToken() {
		Platform platform = platformDao.findOne(getPlatformId());

		String cKey = COOKIE_REMEMBER_TOKEN + platform.getKey();
		Cookies cookies = Cookies.from(getRequest());
		return cookies.getValue(cKey);
	}

	public Long getRememberId() {
		Platform platform = platformDao.findOne(getPlatformId());

		String cKey = COOKIE_REMEMBER_ID + platform.getKey();
		Cookies cookies = Cookies.from(getRequest());
		return cookies.exist(cKey) ? Longs.tryParse(cookies.getValue(cKey)) : null;
	}
}
