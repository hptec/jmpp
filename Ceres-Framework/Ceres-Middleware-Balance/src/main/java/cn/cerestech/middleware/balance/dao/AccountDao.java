package cn.cerestech.middleware.balance.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.middleware.balance.entity.Account;

public interface AccountDao extends JpaRepository<Account, Long> {

	public Account findByOwnerAndType(Owner owner, String type);
}
