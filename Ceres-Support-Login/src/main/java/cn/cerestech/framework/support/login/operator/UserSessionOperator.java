package cn.cerestech.framework.support.login.operator;

import cn.cerestech.framework.support.starter.operator.ResponseOperator;
import cn.cerestech.framework.support.starter.operator.SessionOperator;

public interface UserSessionOperator extends SessionOperator, ResponseOperator {

	public static final String SESSION_LOGINENTITY_ID = "SESSION_LOGINENTITY_ID_";

	public static final String COOKIE_REMEMBER_TOKEN = "COOKIE_REMEMBER_TOKEN_";
	public static final String COOKIE_REMEMBER_ID = "COOKIE_REMEMBER_ID_";

	default void putUserId(Long id) {
		String sKey = SESSION_LOGINENTITY_ID;
		putSession(sKey, id);
	}

	default Long getUserId() {
		String sKey = SESSION_LOGINENTITY_ID;
		return getSession(sKey);
	}

}
