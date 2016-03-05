package cn.cerestech.framework.core;

/**
 * 
 */

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/**
 * @author xiatian
 *
 */
public class AppSessionContext {

	private static AppSessionContext instance;
	private HashMap<String, HttpSession> sessionMap;

	private AppSessionContext() {
		sessionMap = new HashMap<>();
	}

	public synchronized static AppSessionContext getInstance() {
		if (instance == null) {
			instance = new AppSessionContext();
		}
		return instance;
	}

	public synchronized void addSession(HttpSession session) {
		if (session != null) {
			sessionMap.put(session.getId(), session);
		}
	}

	public synchronized void delSession(HttpSession session) {
		if (session != null) {
			sessionMap.remove(session.getId());
		}
	}

	public synchronized HttpSession getSession(String sessionId) {
		if (sessionId == null)
			return null;
		return (HttpSession) sessionMap.get(sessionId);
	}

	public synchronized int getSessionMapSize() {
		if (sessionMap != null) {
			return this.sessionMap.size();
		}
		return 0;
	}

	public synchronized HashMap<String,HttpSession> getSessionMap() {
		return this.sessionMap;
	}

}
