package cn.cerestech.framework.support.login.provider;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.core.strings.StringTypes;
import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.login.entity.Loginable;
import cn.cerestech.framework.support.web.operator.RequestOperator;

public interface LoginProvider<T extends Loginable> extends RequestOperator {

	public static final String LOGIN_REMEMBER = "remember";

	/**
	 * 返回校验结果
	 * 
	 * @return
	 */
	Result<Long> validate();

	/**
	 * 返回处理实体存储的Dao
	 * 
	 * @return
	 */
	LoginDao<T> getDao();

	default public Boolean getRemember() {
		return new StringTypes(getRequest(LOGIN_REMEMBER)).boolValue();
	}
}
