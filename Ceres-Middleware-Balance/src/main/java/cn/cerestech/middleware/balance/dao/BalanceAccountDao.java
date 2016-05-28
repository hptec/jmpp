package cn.cerestech.middleware.balance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.middleware.balance.entity.BalanceAccount;

public interface BalanceAccountDao extends JpaRepository<BalanceAccount, Long> {

	public List<BalanceAccount> findByOwnerIdAndOwnerType(Long id, String type);
}
