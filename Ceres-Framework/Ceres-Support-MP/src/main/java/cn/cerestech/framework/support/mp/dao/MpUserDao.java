package cn.cerestech.framework.support.mp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.framework.support.mp.entity.MpUser;

public interface MpUserDao  extends JpaRepository<MpUser, Long>{

}
