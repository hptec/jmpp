package cn.cerestech.middleware.aliyunoss.provider;

import cn.cerestech.framework.core.Provider;

public interface AliyunOssProvider extends Provider{

	/**
	 * 得到内网访问节点地址
	 * 
	 * @return
	 */
	public String getInternalEndPoint();

	/**
	 * 得到（外网）访问节点地址
	 * 
	 * @return
	 */
	public String getEndPoint();

	/**
	 * 得到节点名称
	 * 
	 * @return
	 */
	public String getName();
}
