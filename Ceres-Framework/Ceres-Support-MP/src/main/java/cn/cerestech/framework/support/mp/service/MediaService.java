package cn.cerestech.framework.support.mp.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.support.mp.entity.base.MpMaterialGov;
import cn.cerestech.framework.support.mp.entity.base.MpNewsMaterial;
import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.mpapi.MaterialAPI.MaterialType;
import cn.cerestech.framework.support.mp.mpapi.cache.strategy.MemoryStrategy;

/**
 * 素材管理
 * 
 * @author harryhe
 *
 */
@Service
public class MediaService {
	@Autowired
	MpConfigService mpConfig;

	/**
	 * 创建永久素材
	 * @param file
	 * @param type
	 * @param video_title type == MaterialType.VIDEO 时有效
	 * @param video_desc  type == MaterialType.VIDEO 时有效
	 */
	public void create(File file, MaterialType type, String video_title, String video_desc){
		MemoryStrategy.of(mpConfig.getAppid(), mpConfig.getAppsecret()).MATERIAL()
			.create(file, type, video_title, video_desc);
	}
	
	/**
	 * 创建临时素材，服务器三天后删除
	 * @param file
	 * @param type
	 */
	public void createTemp(File file, MaterialType type){
		MemoryStrategy.of(mpConfig.getAppid(), mpConfig.getAppsecret()).MATERIAL()
			.createTemp(file, type);
	}
	
	/**
	 * 创建单图文或者多图文消息
	 * 
	 * @param materials
	 * @return 如果成功，则返回的object 中media_id否则，返回失败的消息
	 */
	public Status<String> createNews(MpNewsMaterial...materials){
		Status<MpMaterialGov> status = MemoryStrategy.of(mpConfig.getAppid(), mpConfig.getAppsecret()).MATERIAL()
			.createNews(materials);
		if(status.isSuccess()){
			return new Status<String>(0).setObject(status.getObject().getMedia_id());
		}else{
			return new Status<String>(status.getCode()).setMsg(status.getMsg());
		}
	}
	
	/**
	 * 创建图文消息中的内部图片
	 * @param file
	 * @return 返回错误消息，或者media_id
	 */
	public Status<String> createNewImg(File file){
		Status<MpMaterialGov> status = MemoryStrategy.of(mpConfig.getAppid(), mpConfig.getAppsecret()).MATERIAL()
				.createNewsImg(file);
		
		if(status.isSuccess()){
			return new Status<String>(0).setObject(status.getObject().getMedia_id());
		}else{
			return new Status<String>(status.getCode()).setMsg(status.getMsg());
		}
	}
	

}
