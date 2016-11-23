package cn.cerestech.framework.support.storage.provider;

import cn.cerestech.framework.support.storage.entity.StorageFile;

/**
 * 第三方存储
 * 
 * @author harryhe
 *
 */
public interface ThirdStorageProvider {

	/**
	 * 将图片上传到第三方，并整理httpUri后返回
	 * 
	 * @param fileId
	 * @return
	 */
	StorageFile upload(Long fileId);
}
