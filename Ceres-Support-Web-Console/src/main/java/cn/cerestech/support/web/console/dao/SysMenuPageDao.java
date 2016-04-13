package cn.cerestech.support.web.console.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.support.web.console.entity.SysMenu;
import cn.cerestech.support.web.console.entity.SysMenuPage;

public interface SysMenuPageDao extends JpaRepository<SysMenuPage, Long> {

	List<SysMenuPage> findByPlatformId(Long platformId);
}