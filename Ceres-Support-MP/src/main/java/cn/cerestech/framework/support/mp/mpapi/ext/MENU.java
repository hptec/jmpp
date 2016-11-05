package cn.cerestech.framework.support.mp.mpapi.ext;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import cn.cerestech.framework.support.mp.entity.base.MpMenu;
import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.mpapi.MenuAPI;
import cn.cerestech.framework.support.mp.mpapi.cache.Executor;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年5月25日
 */
public class MENU extends MenuAPI{
	private Executor exec;
	public MENU(Executor exec) {
		super(exec.getAppid(), exec.getAppsecret());
		this.exec = exec;
	}
	
	public <T> Status<T> create(final String menustr) {
		Status<T> status = exec.execute(token->{
			Status<T> s = create(token.getToken(), menustr);
			return s;
		});
		return status;
	}
	
	public Status<List<MpMenu>> cur(String server_acces_token){
		Status<List<MpMenu>> status = exec.execute(token->{
			Status<List<MpMenu>> s = cur(token.getToken());
			return s;
		});
		return status;
	}
	
	public <T> Status<T> del(){
		Status<T> status = exec.execute(token->{
			Status<T> s= del(token.getToken());
			return s;
		});
		return status;
	}
}
