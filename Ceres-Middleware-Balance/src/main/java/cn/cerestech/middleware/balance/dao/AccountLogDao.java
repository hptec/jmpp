package cn.cerestech.middleware.balance.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.cerestech.middleware.balance.entity.AccountLog;

public interface AccountLogDao extends JpaRepository<AccountLog, Long>, JpaSpecificationExecutor<AccountLog> {

	/**
	 * 找到最近的小于0的记录
	 * 
	 * @param accountId
	 * @param zero
	 * @return
	 */
	AccountLog findTopByAccountIdOrderByIdDesc(Long accountId);
}
