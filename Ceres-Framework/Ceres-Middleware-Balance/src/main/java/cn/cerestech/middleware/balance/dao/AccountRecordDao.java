package cn.cerestech.middleware.balance.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.middleware.balance.entity.AccountRecord;

public interface AccountRecordDao extends JpaRepository<AccountRecord, Long> {

	/**
	 * 获取某个账户下还没有使用完的获得记录
	 * 
	 * @param id
	 * @param zero
	 * @return
	 */
	List<AccountRecord> findByAccountIdAndLeftAmountGreaterThanOrderByCreateTime(Long id, BigDecimal zero);

	/**
	 * 查找所有获得记录中还保有剩余金额并且已经到了过期时间的记录，用于做过期处理
	 * 
	 * @param owner
	 * @param type
	 * @param than
	 * @return
	 */
	List<AccountRecord> findByLeftAmountGreaterThanAndExpiredTimeLessThan(BigDecimal zero, Date date);
}
