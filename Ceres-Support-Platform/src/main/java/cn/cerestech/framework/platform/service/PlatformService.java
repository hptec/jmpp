package cn.cerestech.framework.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.platform.dao.PlatformDao;
import cn.cerestech.framework.platform.entity.Platform;
import cn.cerestech.framework.platform.provider.PlatformIdentifyProvider;

@Service
public class PlatformService {

	@Autowired
	PlatformDao platformDao;

	@Autowired()
	PlatformIdentifyProvider indentifyProvider;

	public Platform getPlatform() {
		return getIndentifyProvider().get();
	}

	public PlatformIdentifyProvider getIndentifyProvider() {
		return indentifyProvider;
	}

}
