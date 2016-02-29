package cn.cerestech.framework.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.platform.dao.PlatformDao;
import cn.cerestech.framework.platform.entity.Platform;
import cn.cerestech.framework.platform.provider.DefaultPlatformIdentifyProvider;
import cn.cerestech.framework.platform.provider.PlatformIdentifyProvider;

@Service
public class PlatformService {

	@Autowired
	PlatformDao platformDao;

	@Autowired
	DefaultPlatformIdentifyProvider defaultIndentifyProvider;

	@Autowired(required = false)
	PlatformIdentifyProvider indentifyProvider;

	public Platform getPlatform() {
		return (indentifyProvider == null ? defaultIndentifyProvider : indentifyProvider).get();
	}

	public void recive(String beanName, Object bean) {
		if (bean instanceof PlatformIdentifyProvider) {
			if (!(bean instanceof DefaultPlatformIdentifyProvider)) {
				// 默认不的Provider不注入
			} else {

				if (indentifyProvider != null) {
					throw new IllegalArgumentException(
							"PlatformIdentifyProvider conflict! " + bean.getClass().getCanonicalName());
				} else {
					indentifyProvider = (PlatformIdentifyProvider) bean;
				}
			}
		}
	}

	public void onComplete() {

	}

}
