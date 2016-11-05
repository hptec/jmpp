package cn.cerestech.framework.support.mp.mpapi.ext;

import org.apache.poi.ss.formula.functions.T;

import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.mpapi.OthersAPI;
import cn.cerestech.framework.support.mp.mpapi.cache.Executor;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年5月25日
 */
public class OTHERS extends OthersAPI{

	private Executor exec;
	public OTHERS(Executor exec){
		super(exec.getAppid(), exec.getAppsecret());
		this.exec = exec;
	}
	public <T> Status<T> createQrImg(String scene_str, Integer scene_id, boolean isTmp, Long tmpExpiredTimeSeconds){
		Status<T> status = exec.execute(token->{
			Status<T> s = createQrImg(token.getToken(), scene_str, scene_id, isTmp, tmpExpiredTimeSeconds);
			return s;
		});
		return status;
	}
	
	public <T> Status<T> createTmpQr(int scene_id, Long expired_times){
		Status<T> status = exec.execute(token->{
			Status<T> s = createTmpQr(token.getToken(), scene_id, expired_times);
			return s;
		});
		return status;
	}
	
	public <T> Status<T> crateQr(String server_access_token, String scene_str, Integer or_scene_id){
		Status<T> status = exec.execute(token->{
			Status<T> s = crateQr(token.getToken(), scene_str, or_scene_id);
			return s;
		});
		return status;
	}
	
	public Status<String> getQrImg(String filePath){
		Status<String> status = exec.execute(token->{
			Status<String> s = getQrImg(token.getToken(), filePath);
			return s;
		});
		return status;
	}
	
	public Status<String> shorUrl(String src_url){
		Status<String> status = exec.execute(token->{
			Status<String> s = shorUrl(token.getToken(), src_url);
			return s;
		});
		return status;
	}
}
