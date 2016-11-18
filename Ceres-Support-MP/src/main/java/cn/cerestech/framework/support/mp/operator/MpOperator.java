package cn.cerestech.framework.support.mp.operator;

import java.util.Enumeration;

import com.google.common.base.Strings;

import cn.cerestech.framework.support.mp.entity.MpUser;
import cn.cerestech.framework.support.mp.entity.base.MpUserToken;
import cn.cerestech.framework.support.starter.operator.RequestOperator;
import cn.cerestech.framework.support.starter.operator.SessionOperator;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月6日
 */
public interface MpOperator extends SessionOperator, RequestOperator {
	public static final String SESSION_MPUSER_ID_KEY = "_SESSION_MPUSER_IDKEY";
	public static final String SESSION_MPUSER_OPENID = "_SESSION_MPUSER_OPENID";
	public static final String SESSION_MPUSER_TOKEN = "_SESSION_MPUSER_TOKEN";
	
	/**
	 * 获取当前登录的微信用户
	 * @return
	 */
	public default Long getMpUserId(){
		return getSession(SESSION_MPUSER_ID_KEY);
	}
	
	public  default MpUserToken getToken(){
		return getSession(SESSION_MPUSER_TOKEN);
	}
	
	/**
	 * 获取openid
	 */
	public default String getSessionOpenid(){
		return getSession(SESSION_MPUSER_OPENID);
	}
	public default void setSessionState(MpUser mpuser){
		putSession(SESSION_MPUSER_ID_KEY, mpuser.getId());
		putSession(SESSION_MPUSER_OPENID, mpuser.getOpenId());
	}
	
	public default void clear(){
		removeSession(SESSION_MPUSER_ID_KEY);
		removeSession(SESSION_MPUSER_OPENID);
	}
	
	public default String getRequestUriWithParams(){
		Enumeration<String> e = getRequest().getParameterNames();
		String queryStr = "";
		while(e.hasMoreElements()){
			String name = e.nextElement();
			String val = getRequest(name);
			if(!"code".equalsIgnoreCase(name) && !Strings.isNullOrEmpty(val) && !"state".equalsIgnoreCase(name)){
				if(queryStr.length() >0){
					queryStr += "&";
				}
				queryStr += name+"="+getRequest().getParameter(name);
			}
		}
		
		String uri = getRequest().getRequestURI(); 
		if(!Strings.isNullOrEmpty(queryStr)){
			uri += "?"+queryStr;
		}
		return uri;
	}
	
}
