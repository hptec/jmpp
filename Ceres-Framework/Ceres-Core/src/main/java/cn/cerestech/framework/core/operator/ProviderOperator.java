package cn.cerestech.framework.core.operator;

import cn.cerestech.framework.core.annotation.Provider;
import cn.cerestech.framework.core.enums.PlatformCategory;

public interface ProviderOperator {

	/**
	 * 获取Provider注解中注册的平台类型
	 * 
	 * @return
	 */
	default PlatformCategory forCategory() {
		Provider provider = getClass().getAnnotation(Provider.class);
		return provider == null ? null : provider.value();
	}
}
