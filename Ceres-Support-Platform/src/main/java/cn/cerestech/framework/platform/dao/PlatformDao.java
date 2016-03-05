package cn.cerestech.framework.platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.framework.platform.entity.Platform;

public interface PlatformDao extends JpaRepository<Platform, Long> {

	Platform findByAppId(String appId);
}
