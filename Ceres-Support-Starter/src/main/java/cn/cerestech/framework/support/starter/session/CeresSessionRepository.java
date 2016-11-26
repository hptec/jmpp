package cn.cerestech.framework.support.starter.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSession;
import org.springframework.session.SessionRepository;

import com.google.common.base.Strings;

import cn.cerestech.framework.support.starter.operator.RequestOperator;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月26日
 */
public class CeresSessionRepository implements SessionRepository<ExpiringSession>, RequestOperator{
	private Integer defaultMaxInactiveInterval;
	private final Map<String, ExpiringSession> sessions;
	private static final String SESSION_ID_CUSTOMER = "platform_client_sid";
	
	public CeresSessionRepository(){
		this(new ConcurrentHashMap<String, ExpiringSession>());
	}
	
	private CeresSessionRepository(ConcurrentHashMap<String, ExpiringSession> sessions){
		if (sessions == null) {
			throw new IllegalArgumentException("sessions cannot be null");
		}
		this.sessions = sessions;
	}
	
	private String clientSid(){
		return getRequest(SESSION_ID_CUSTOMER);
	}
	
	@Override
	public ExpiringSession createSession() {
		String sid = clientSid();
		ExpiringSession result = null;
		if(Strings.isNullOrEmpty(sid)){
			result = new MapSession();
		}else{
			result = this.sessions.get(sid);
			if(result == null){
				result = new MapSession(sid);
			}
		}
		if (this.defaultMaxInactiveInterval != null) {
			result.setMaxInactiveIntervalInSeconds(this.defaultMaxInactiveInterval);
		}
		return result;
	}

	@Override
	public void save(ExpiringSession session) {
		this.sessions.put(session.getId(), new MapSession(session));
	}

	@Override
	public ExpiringSession getSession(String id) {
		String sidClient = clientSid();
		if(!Strings.isNullOrEmpty(sidClient) && !sidClient.equalsIgnoreCase(id)){
			return null;
		}
		ExpiringSession saved = this.sessions.get(id);
		if (saved == null) {
			return null;
		}
		if (saved.isExpired()) {
			delete(saved.getId());
			return null;
		}
		return new MapSession(saved);
	}

	@Override
	public void delete(String id) {
		this.sessions.remove(id);
	}

}
