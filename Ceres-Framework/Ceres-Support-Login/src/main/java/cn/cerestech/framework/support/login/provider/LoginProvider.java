package cn.cerestech.framework.support.login.provider;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.login.entity.Loginable;

public interface LoginProvider<T extends Loginable> {

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
}
