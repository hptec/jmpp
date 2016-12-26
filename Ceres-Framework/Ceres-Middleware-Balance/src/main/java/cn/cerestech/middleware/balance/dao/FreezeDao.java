package cn.cerestech.middleware.balance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.cerestech.middleware.balance.entity.Freeze;
import cn.cerestech.middleware.balance.enums.FreezeState;

public interface FreezeDao extends JpaRepository<Freeze, Long>,JpaSpecificationExecutor<Freeze> {
	
	/**
	 * 获取指定账户的冻结记录
	 * @param id
	 * @param state
	 * @return
	 */
	List<Freeze> findByAccountIdAndStateOrderByCreateTimeDesc(Long accountId,FreezeState state);
	
	
	List<Freeze> findByAccountIdOrderByCreateTimeDesc(Long accountId);
}
