package cn.cerestech.framework.support.starter.operator;

import cn.cerestech.framework.support.starter.entity.Platform;

public interface PlatformOperator extends RequestOperator, SessionOperator {

	public static final String PLATFORM_ID = "PLATFORM_ID";
	public static final String PLATFORM_OBJECT = "PLATFORM_OBJECT";
	public static final String PLATFORM_KEY = "ceres_platform_key";
	// public static final String COOKIE_CERES_PLATFORM_ID =
	// "COOKIE_CERES_PLATFORM_ID";

//	default Platform getPlatform() {
//		// 首先检查Session中是否存放Platform.Id
//		Long id = getSession(SESSION_PLATFORM_ID);
//		if(id!=null)
//
//		String strPlatform = getRequest(REQUEST_CERES_PLATFORM);
//		if (Strings.isNullOrEmpty(strPlatform)) {
//			// 如果Request中获取不到就从Cookie取
//			Cookies cookies = Cookies.from(getRequest());
//			strPlatform = cookies.getValue(REQUEST_CERES_PLATFORM);
//		}
//		if (Strings.isNullOrEmpty(strPlatform)) {
//			return PlatformCategory.NULL;
//		} else {
//			return EnumCollector.forClass(PlatformCategory.class).keyOf(strPlatform);
//		}
//	}

	/**
	 * 从Session中获取Platform的ID
	 * 
	 * @return
	 */
	default Long getPlatformId() {
		return getSession(PLATFORM_ID);
	}

	/**
	 * 从Reqeust中获取提交上来的PlatformKey
	 * 
	 * @return
	 */
	default String getPlatformKey() {
		return getRequest(PLATFORM_KEY);
	}

	/**
	 * 得到Platform数据库对象。要么设置PlatformRequried.preload=true，要么getPlatformId()自己读取，
	 * 否则取出为空
	 * 
	 * @return
	 */
	default Platform getPlatform() {
		return getRequest(PLATFORM_OBJECT);
	}

}
