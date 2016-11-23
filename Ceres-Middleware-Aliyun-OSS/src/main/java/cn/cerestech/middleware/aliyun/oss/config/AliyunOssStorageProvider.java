package cn.cerestech.middleware.aliyun.oss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.storage.entity.StorageFile;
import cn.cerestech.framework.support.storage.provider.ThirdStorageProvider;
import cn.cerestech.framework.support.storage.service.StorageService;
import cn.cerestech.middleware.aliyun.oss.service.AliyunOssService;

@Primary
@Component
public class AliyunOssStorageProvider implements ThirdStorageProvider {

	@Autowired
	StorageService storageService;

	@Autowired
	AliyunOssService aliyunOssService;

	@Override
	public StorageFile upload(Long fileId) {
		Result<StorageFile> fileRet = storageService.queryById(fileId);
		if (!fileRet.isSuccess()) {
			return null;
		}

		StorageFile file = fileRet.getObject();
		Result<StorageFile> result = aliyunOssService.upload(file);
		if (result.isSuccess()) {
			return result.getObject();
		} else {
			return null;
		}

	}

}
