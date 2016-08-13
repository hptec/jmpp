package cn.cerestech.framework.support.starter.console.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.framework.support.starter.console.entity.SysMenu;

public interface SysMenuDao extends JpaRepository<SysMenu, Long> {

	public List<SysMenu> findByParentIsNull();

	public SysMenu findByUuid(String uuid);
}
