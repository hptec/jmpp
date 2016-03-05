package cn.cerestech.framework.core;


import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	private AppSessionContext myc = AppSessionContext.getInstance();
	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		myc.addSession(httpSessionEvent.getSession());
		System.out.println("current Session Num: "+myc.getSessionMapSize());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		 HttpSession session = httpSessionEvent.getSession();
	     myc.delSession(session);
	}
	
}
