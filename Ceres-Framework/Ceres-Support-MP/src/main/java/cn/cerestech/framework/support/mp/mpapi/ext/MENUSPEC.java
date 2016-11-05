package cn.cerestech.framework.support.mp.mpapi.ext;

import java.util.List;

import cn.cerestech.framework.support.mp.entity.base.MpMenu;
import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.mpapi.MenuSpecAPI;
import cn.cerestech.framework.support.mp.mpapi.cache.Executor;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年5月25日
 */
public class MENUSPEC extends MenuSpecAPI{
	private Executor exec;
	public MENUSPEC(Executor exec){
		super(exec.getAppid(), exec.getAppsecret());
		this.exec = exec;
	}
	public Status<Long> create(String spec_menu_str) {
		Status<Long> status = exec.execute(token->{
			Status<Long> s = super.create(token.getToken(), spec_menu_str);
			return s;
		});
		return status;
	}
	public Status<List<MpMenu>> queryMpuserMenu(final String openid_or_wxh){
		Status<List<MpMenu>> status = exec.execute(token->{
			Status<List<MpMenu>> s = super.queryMpuserMenu(token.getToken(), openid_or_wxh);
			return s;
		});
		return status;
	}
	
	public Status delMenu(long menuid){
		Status status = exec.execute(token->{
			Status s = super.delMenu(token.getToken(), menuid);
			return s;
		});
		return status;
	}
}
