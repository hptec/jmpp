package cn.cerestech.framework.support.mp.mpapi.ext;

import cn.cerestech.framework.support.mp.entity.base.MpWaiter;
import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.mpapi.WaiterAPI;
import cn.cerestech.framework.support.mp.mpapi.cache.Executor;
import cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg.comm.MpServiceMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年5月25日
 */
public class WAITERAPI extends WaiterAPI{
	private Executor exec;
	public WAITERAPI(Executor exec){
		super(exec.getAppid(), exec.getAppsecret());
		this.exec = exec;
	}
	public Status<MpWaiter> create(String acc, String pwd_md5, String nick) {
		Status<MpWaiter> status = exec.execute(token->{
			Status<MpWaiter> s = create(token.getToken(), acc, pwd_md5, nick);
			return s;
		});
		return status;
	}
	
	public Status<String> sendMsg(MpServiceMsg msg){
		Status<String> status = exec.execute(token->{
			Status<String> s = sendMsg(msg, token.getToken());
			return s;
		});
		return status;
	} 
}
