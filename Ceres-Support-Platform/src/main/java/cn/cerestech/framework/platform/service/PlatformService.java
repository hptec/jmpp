package cn.cerestech.framework.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.platform.dao.PlatformDao;
import cn.cerestech.framework.platform.entity.Platform;

@Service
public class PlatformService {

	@Autowired
	PlatformDao platformDao;

	/**
	 * 对信息进行认证，认证成功放入缓存
	 * 
	 * @param key
	 * @param secret
	 * @return
	 */
	public Platform getPlatform() {
		Platform platform = platformDao.findOne(1L);
		if (platform == null) {
			platform = new Platform();
			platform.setId(1L);
			platformDao.save(platform);
		}

		return platform;
	}

}
