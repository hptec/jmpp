package cn.cerestech.framework.support.storage.dao;

import org.springframework.data.repository.CrudRepository;

import cn.cerestech.framework.support.storage.entity.LocalFile;

public interface LocalFileDao extends CrudRepository<LocalFile, Long> {

	public LocalFile findByHttpUri(String httpUri);

	public LocalFile findByLocalUri(String local_uri);
}
