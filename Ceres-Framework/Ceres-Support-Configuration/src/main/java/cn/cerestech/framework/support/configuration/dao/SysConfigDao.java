package cn.cerestech.framework.support.configuration.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.framework.support.configuration.entity.SysConfig;

public interface SysConfigDao extends JpaRepository<SysConfig, Long> {

	public SysConfig findByPlatformIdAndKey(Long platformId, String key);
}
