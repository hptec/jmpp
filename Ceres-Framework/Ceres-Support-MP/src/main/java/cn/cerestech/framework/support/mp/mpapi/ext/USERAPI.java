package cn.cerestech.framework.support.mp.mpapi.ext;

import java.util.List;
import java.util.Set;

import cn.cerestech.framework.support.mp.entity.MpUser;
import cn.cerestech.framework.support.mp.entity.base.MpGroup;
import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.mpapi.UserAPI;
import cn.cerestech.framework.support.mp.mpapi.cache.Executor;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年5月25日
 */
public class USERAPI extends UserAPI{
	private Executor exec;
	
	public USERAPI(Executor exec){
		super(exec.getAppid(), exec.getAppsecret());
		this.exec = exec;
	}
	public Status<MpUser> get(final String openid){
		Status<MpUser> status = exec.execute(token->{
			Status<MpUser> s = get(token.getToken(), openid);
			return s;
		});
		return status;
	}
	public Status<List<String>> batchOpenides(final String next_openid){
		Status<List<String>> status = exec.execute(token->{
			Status<List<String>> s = batchOpenides(token.getToken(), next_openid);
			return s;
		});
		return status;
	}
	public Status<MpGroup> createGroup(final String gp_names){
		Status<MpGroup> status = exec.execute(token->{
			Status<MpGroup> s = createGroup(token.getToken(), gp_names);
			return s;
		});
		return status;
	}
	
	public Status<MpGroup> updateGroup(final long group_id, final String name){
		Status<MpGroup> status = exec.execute(token->{
			Status<MpGroup> s = updateGroup(token.getToken(), group_id, name);
			return s;
		});
		return status;
	}
	
	public Status delGroup(final long group_id){
		Status status = exec.execute(token->{
			Status s = delGroup(token.getToken(), group_id);
			return s;
		});
		return status;
	}
	
	public Status<List<MpGroup>> groups(String server_access_token){
		Status<List<MpGroup>> status = exec.execute(token->{
			Status<List<MpGroup>> s = groups(token.getToken());
			return s;
		});
		return status;
	}
	
	public Status<MpGroup> inGroup(final String openid){
		Status<MpGroup> status = exec.execute(token->{
			Status<MpGroup> s = inGroup(token.getToken(), openid);
			return s;
		});
		return status;
	}
	
	public Status assignGroup(String openid, long group_id){
		Status status = exec.execute(token->{
			Status s = assignGroup(token.getToken(), openid, group_id);
			return s;
		});
		return status;
	}
	
	public Status assignGroupBatch(Set<String> openides, long group_id){
		Status status = exec.execute(token->{
			Status s = assignGroupBatch(token.getToken(), openides, group_id);
			return s;
		});
		return status;
	}
	
	public Status assignNick(final String openid, final String nick){
		Status status = exec.execute(token->{
			Status s = assignNick(token.getToken(), openid, nick);
			return s;
		});
		return status;
	}
}
