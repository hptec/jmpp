package cn.cerestech.middleware.balance.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.cerestech.middleware.balance.entity.AccountLog;

public interface AccountLogDao extends JpaRepository<AccountLog, Long>,JpaSpecificationExecutor<AccountLog> {

}
