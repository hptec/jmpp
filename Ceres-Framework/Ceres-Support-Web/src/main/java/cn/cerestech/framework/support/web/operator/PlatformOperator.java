package cn.cerestech.framework.support.web.operator;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.enums.PlatformCategory;

public interface PlatformOperator extends RequestOperator {

	public static final String REQUEST_CERES_PLATFORM = "ceres_platform";
	public static final String COOKIE_CERES_PLATFORM_ID = "COOKIE_CERES_PLATFORM_ID";

	default PlatformCategory getPlatformCategory() {
		// Cookies cookies = Cookies.from(getRequest());
		String strPlatform = getRequest(REQUEST_CERES_PLATFORM);
		if (Strings.isNullOrEmpty(strPlatform)) {
			return null;
		} else {
			return EnumCollector.forClass(PlatformCategory.class).keyOf(strPlatform);
		}
	}
}
