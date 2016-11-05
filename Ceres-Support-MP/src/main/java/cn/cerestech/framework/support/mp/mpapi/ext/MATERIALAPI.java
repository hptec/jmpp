package cn.cerestech.framework.support.mp.mpapi.ext;

import java.io.File;
import java.util.Map;

import cn.cerestech.framework.support.mp.entity.base.MpMaterial;
import cn.cerestech.framework.support.mp.entity.base.MpNewsMaterial;
import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.mpapi.MaterialAPI;
import cn.cerestech.framework.support.mp.mpapi.cache.Executor;


/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年5月25日
 */
public class MATERIALAPI extends MaterialAPI {
	private Executor exec;
	public MATERIALAPI(Executor exec) {
		super(exec.getAppid(), exec.getAppsecret());
		this.exec = exec;
	}
	
	public Status<MpMaterial> createTemp(File file, MaterialType type){
		Status<MpMaterial> status = exec.execute(token->{
			Status<MpMaterial> s = createTemp(token.getToken(), file, type);
			return s;
		});
		return status;
	}
	
	public Status<MpMaterial> create(File file, MaterialType type, String video_title, String video_desc){
		Status<MpMaterial> status = exec.execute(token->{
			Status<MpMaterial> s = create(token.getToken(), file, type, video_title, video_desc);
			return s;
		});
		return status;
	}
	
	public Status<Map<String, String>> get(String media_id){
		Status<Map<String, String>> status = exec.execute(token->{
			Status<Map<String, String>> s = get(token.getToken(), media_id);
			return s;
		});
		return status;
	}
	
	public Status del(String media_id){
		Status status = exec.execute(token->{
			Status s = del(token.getToken(), media_id);
			return s;
		});
		return status;
	}
	
	public Status<MpMaterial> createNewsImg(File file){
		Status<MpMaterial> status = exec.execute(token->{
			Status<MpMaterial> s = createNewsImg(token.getToken(), file);
			return s;
		});
		return status;
	}
	
	public Status<MpMaterial> createNews(MpNewsMaterial... materials){
		Status<MpMaterial> status = exec.execute(token->{
			Status<MpMaterial> s = createNews(token.getToken(), materials);
			return s;
		});
		return status;
	}
	
	public Status updateNews(String news_media_id, MpNewsMaterial material, int index){
		Status status = exec.execute(token->{
			Status s = updateNews(token.getToken(), news_media_id, material, index);
			return s;
		});
		return status;
	}
	public Status<Map<String, Integer>> count(){
		Status<Map<String, Integer>> status = exec.execute(token->{
			Status<Map<String, Integer>> s= count(token.getToken());
			return s;
		});
		return status;
	}
	
	public Status getBatch(MaterialType type, int offset, int count){
		Status status = exec.execute(token->{
			Status s = getBatch(token.getToken(), type, offset, count);
			return s;
		});
		return status;
	}
}
