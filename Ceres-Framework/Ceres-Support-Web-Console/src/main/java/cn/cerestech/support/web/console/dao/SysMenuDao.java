package cn.cerestech.support.web.console.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.support.web.console.entity.SysMenu;

public interface SysMenuDao extends JpaRepository<SysMenu, Long> {

	List<SysMenu> findByPlatformId(Long platformId);
}