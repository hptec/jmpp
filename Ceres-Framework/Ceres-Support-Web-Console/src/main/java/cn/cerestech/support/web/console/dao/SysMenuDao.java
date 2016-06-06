package cn.cerestech.support.web.console.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.support.web.console.entity.SysMenu;

public interface SysMenuDao extends JpaRepository<SysMenu, Long> {

	public void deleteByKeyIn(Set<String> set);
}
