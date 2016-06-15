package cn.cerestech.framework.support.storage.dao;

import org.springframework.data.repository.CrudRepository;

import cn.cerestech.framework.support.storage.entity.StorageFile;

public interface StorageDao extends CrudRepository<StorageFile, Long> {

	public StorageFile findUniqueByHttpUri(String httpUri);

	public StorageFile findUniqueByLocalUri(String local_uri);

	public StorageFile findUniqueByUploadName(String upload_uri);
}
