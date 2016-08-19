package cn.cerestech.framework.support.starter.operator;

import com.google.common.base.Strings;

import cn.cerestech.framework.support.starter.entity.Platform;

public interface PlatformOperator extends RequestOperator, SessionOperator {

	public static final String PLATFORM_ID = "PLATFORM_ID";
	public static final String PLATFORM_OBJECT = "PLATFORM_OBJECT";
	public static final String PLATFORM_KEY = "ceres_platform_key";

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
		String key = getRequest(PLATFORM_KEY);
		return key;
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
