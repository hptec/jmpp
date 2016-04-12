package cn.cerestech.framework.support.localstorage.dao;

import org.springframework.data.repository.CrudRepository;

import cn.cerestech.framework.support.localstorage.entity.LocalFile;

public interface LocalFileDao extends CrudRepository<LocalFile, Long> {

	public LocalFile findByPlatformIdAndHttpUri(Long platformId, String httpUri);

	public LocalFile findByPlatformIdAndLocalUri(Long platformId, String local_uri);
}
