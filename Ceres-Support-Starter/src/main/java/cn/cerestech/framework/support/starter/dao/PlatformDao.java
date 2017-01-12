package cn.cerestech.framework.support.starter.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.framework.support.starter.entity.Platform;

public interface PlatformDao extends JpaRepository<Platform, Long> {

	Platform findTopByKey(String id);
}
