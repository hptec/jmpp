package cn.cerestech.framework.platform.provider;

import java.util.function.Supplier;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.platform.entity.Platform;

public interface PlatformIdentifyProvider extends Supplier<Platform> {

	Result<Platform> authentication(String key, String secret);

	Long getId();
}
