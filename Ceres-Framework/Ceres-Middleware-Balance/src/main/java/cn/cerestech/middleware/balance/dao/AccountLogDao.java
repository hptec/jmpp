package cn.cerestech.middleware.balance.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.middleware.balance.entity.AccountLog;

public interface AccountLogDao extends JpaRepository<AccountLog, Long> {

}
