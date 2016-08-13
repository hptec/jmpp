package cn.cerestech.framework.support.login.operator;

import com.google.common.base.Strings;
import com.google.common.primitives.Longs;

import cn.cerestech.framework.support.starter.Cookies;
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

//	default String getRememberToken() {
//		String cKey = COOKIE_REMEMBER_TOKEN;
//		Cookies cookies = Cookies.from(getRequest());
//		return cookies.getValue(cKey);
//	}
//
//	default Long getRememberId() {
//		String cKey = COOKIE_REMEMBER_ID;
//		Cookies cookies = Cookies.from(getRequest());
//		return cookies.exist(cKey) ? Longs.tryParse(cookies.getValue(cKey)) : null;
//	}

//	default void putRemember(Long id, String token) {
//		if (id != null && !Strings.isNullOrEmpty(token)) {
//			String cKeyToken = COOKIE_REMEMBER_TOKEN;
//			String cKeyId = COOKIE_REMEMBER_ID;
//			Cookies cookies = Cookies.from(getRequest());
//			cookies.add(cKeyToken, token);
//			cookies.add(cKeyId, id.toString());
//			cookies.flushTo(getResponse());
//		}
//	}

	default void clearRemember() {
		String cKeyToken = COOKIE_REMEMBER_TOKEN;
		String cKeyId = COOKIE_REMEMBER_ID;
		Cookies cookies = Cookies.from(getRequest());
		cookies.remove(cKeyToken);
		cookies.remove(cKeyId);
		cookies.flushTo(getResponse());
	}

}
