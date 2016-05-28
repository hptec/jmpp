package cn.cerestech.framework.support.web.operator;

import javax.servlet.http.HttpSession;

public interface SessionOperator extends RequestOperator {

	/**
	 * 获取当前session
	 * 
	 * @return
	 */
	default HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 判断是否是刚创建的session
	 * 
	 * @return
	 */
	default boolean isNewSession() {
		return getSession().isNew();
	}

	/**
	 * 添加session 缓存数据
	 * 
	 * @param key
	 * @param obj
	 */
	default void putSession(String key, Object obj) {
		getRequest().getSession(true).setAttribute(key, obj);
	}

	/**
	 * 获取session 缓存数据
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default <T> T getSession(String key) {
		return (T) getRequest().getSession(true).getAttribute(key);
	}

	/**
	 * 移除session 中的变量
	 * 
	 * @param key
	 */
	default void removeSession(String key) {
		getRequest().getSession(true).removeAttribute(key);
	}

}
