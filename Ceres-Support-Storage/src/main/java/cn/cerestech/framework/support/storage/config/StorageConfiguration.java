package cn.cerestech.framework.support.storage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.cerestech.framework.support.storage.dao.StorageDao;
import cn.cerestech.framework.support.storage.entity.StorageFile;
import cn.cerestech.framework.support.storage.provider.ThirdStorageProvider;

@Configuration
public class StorageConfiguration {
	@Autowired
	StorageDao storageDao;

	@Bean
	@ConditionalOnMissingClass
	public ThirdStorageProvider getThirdStorageProvider() {
		return new ThirdStorageProvider() {

			@Override
			public StorageFile upload(Long fileId) {
				if (fileId == null) {
					return null;
				}
				StorageFile file = storageDao.findOne(fileId);
				return file;
			}

		};
	}
}
