package cn.cerestech.framework.support.login.provider;

import java.util.List;

import cn.cerestech.framework.core.operator.ProviderOperator;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.core.strings.StringTypes;
import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.login.entity.LoginField;
import cn.cerestech.framework.support.login.entity.Loginable;
import cn.cerestech.framework.support.web.operator.RequestOperator;

public interface LoginProvider<T extends Loginable> extends RequestOperator, ProviderOperator {

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

	List<LoginField> getLoginFields();

	// default public KV getLoginParameters() {
	// KV params = KV.on();
	// getLoginFields().forEach(lf -> {
	// params.put(lf.getName(), getRequest().getParameter(lf.getName()));
	// });
	//
	// params.put(LOGIN_REMEMBER, getRemember());
	// return params;
	// }
}
