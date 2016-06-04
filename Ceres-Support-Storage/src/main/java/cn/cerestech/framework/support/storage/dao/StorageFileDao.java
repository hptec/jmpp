package cn.cerestech.framework.support.storage.dao;

import org.springframework.data.repository.CrudRepository;

import cn.cerestech.framework.support.storage.entity.StorageFile;

public interface StorageFileDao extends CrudRepository<StorageFile, Long> {

	public StorageFile findByHttpUri(String httpUri);

	public StorageFile findByLocalUri(String local_uri);
}
