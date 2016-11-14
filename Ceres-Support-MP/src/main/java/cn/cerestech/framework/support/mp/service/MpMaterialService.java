package cn.cerestech.framework.support.mp.service;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import cn.cerestech.framework.core.date.Dates;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.support.mp.dao.MpMaterialDao;
import cn.cerestech.framework.support.mp.entity.MpMaterial;
import cn.cerestech.framework.support.mp.entity.base.MpMaterialGov;
import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.enums.MpMediaType;
import cn.cerestech.framework.support.mp.mpapi.MaterialAPI.MaterialType;
import cn.cerestech.framework.support.mp.mpapi.cache.strategy.MemoryStrategy;
import cn.cerestech.framework.support.mp.mpapi.ext.MATERIALAPI;
import cn.cerestech.framework.support.persistence.Owner;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月12日
 */
public class MpMaterialService {
	@Autowired
	MpConfigService mpConfig;
	@Autowired
	MpMaterialDao mpMaterialDao;
	private static Object locker = new byte[1];

	/**
	 * 强制刷新素材
	 * @param filePath：本地图片绝对路径
	 * @return
	 */
	public MpMaterial updateImg(String filePath, Owner owner,String key, boolean isTemp){
		synchronized (locker) {
			MpMaterial material = mpMaterialDao.findUniqueByOwnerAndKey(owner, key);
			MATERIALAPI api = MemoryStrategy.of(mpConfig.getAppid(), mpConfig.getAppsecret()).MATERIAL();
			Status<MpMaterialGov> status = null;
			if(isTemp){
				status = api.createTemp(new File(filePath), MaterialType.IMAGE);
			}else{
				status = api.create(new File(filePath), MaterialType.IMAGE, null, null);
			}
			
			if(status.isSuccess()){//
				MpMaterialGov mgov = status.getObject();
				if(material == null){
					material = new MpMaterial();
				}
				material.setTemporary(YesNo.NO);
				material.setCreateTime(new Date());
				if(isTemp){
					material.setTemporary(YesNo.YES);
					long create_time = mgov.getCreated_at();
					if(create_time > 0){
						material.setExpired_time(Dates.from(create_time*1000 + 24*3600*1000*3).toDate());
					}
				}
				material.setOwner(owner);
				material.setKey(key);
				material.setMedia_id(mgov.getMedia_id());
				material.setMedia_url(mgov.getUrl());
				material.setType(MpMediaType.keyOf(mgov.getType()));
				material.setUpdateTime(new Date());
				mpMaterialDao.save(material);
				return material;
			}else{
				return null;
			}
		}
	}
	
	/**
	 * 查找素材，如果过期则刷新，如果不存在则刷新重新上传并返回
	 * @param filePath
	 * @param owner
	 * @param key
	 * @param isTemp
	 * @return
	 */
	public MpMaterial findOrUpdate(String filePath, Owner owner, String key, boolean isTemp){
		synchronized (locker) {
			MpMaterial material = mpMaterialDao.findUniqueByOwnerAndKey(owner, key);
			if(material == null || (YesNo.YES.equals(material.getTemporary()) &&  material.getExpired_time().getTime() > System.currentTimeMillis())){
				MATERIALAPI api = MemoryStrategy.of(mpConfig.getAppid(), mpConfig.getAppsecret()).MATERIAL();
				Status<MpMaterialGov> status = null;
				if(isTemp){
					status = api.createTemp(new File(filePath), MaterialType.IMAGE);
				}else{
					status = api.create(new File(filePath), MaterialType.IMAGE, null, null);
				}
				if(status.isSuccess()){//
					MpMaterialGov mgov = status.getObject();
					material.setTemporary(YesNo.NO);
					material.setCreateTime(new Date());
					if(isTemp){
						material.setTemporary(YesNo.YES);
						long create_time = mgov.getCreated_at();
						if(create_time > 0){
							material.setExpired_time(Dates.from(create_time*1000 + 24*3600*1000*3).toDate());
						}
					}
					material.setOwner(owner);
					material.setKey(key);
					material.setMedia_id(mgov.getMedia_id());
					material.setMedia_url(mgov.getUrl());
					material.setType(MpMediaType.keyOf(mgov.getType()));
					material.setUpdateTime(new Date());
					mpMaterialDao.save(material);
					return material;
				}
				return null;
			}
			return material;
		}
	}
	
}
