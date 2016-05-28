package cn.cerestech.framework.support.localstorage.dao;

import org.springframework.data.repository.CrudRepository;

import cn.cerestech.framework.support.localstorage.entity.LocalFile;

public interface LocalFileDao extends CrudRepository<LocalFile, Long> {

	public LocalFile findByHttpUri(String httpUri);

	public LocalFile findByLocalUri(String local_uri);
}
