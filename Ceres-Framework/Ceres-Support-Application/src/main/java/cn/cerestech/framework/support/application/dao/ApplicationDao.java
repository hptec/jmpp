package cn.cerestech.framework.support.application.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.framework.support.application.entity.Application;

public interface ApplicationDao extends JpaRepository<Application, Long> {

	Application findByAppId(String appId);
}
