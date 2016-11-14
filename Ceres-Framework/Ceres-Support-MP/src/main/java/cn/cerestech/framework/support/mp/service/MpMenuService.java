package cn.cerestech.framework.support.mp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.mpapi.cache.strategy.MemoryStrategy;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月12日
 */
@Service
public class MpMenuService {
	@Autowired
	MpConfigService mpConfig;
	
	public Status<Object> publishMenu(String menuStr){
		return MemoryStrategy.of(mpConfig.getAppid(), mpConfig.getAppsecret())
		.MENU().create(menuStr);
	}
}
