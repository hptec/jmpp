package cn.cerestech.middleware.balance.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.middleware.balance.entity.BalanceAccount;

public interface BalanceMapper {
	
	public List<BalanceAccount> search(@Param("owner")Owner owner);
}
