package cn.cerestech.middleware.balance.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beust.jcommander.internal.Lists;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.framework.support.persistence.entity.Extra;
import cn.cerestech.middleware.balance.config.AbstractBalanceConfig;
import cn.cerestech.middleware.balance.dao.AccountDao;
import cn.cerestech.middleware.balance.dao.AccountLogDao;
import cn.cerestech.middleware.balance.dao.AccountRecordDao;
import cn.cerestech.middleware.balance.dataobject.BalanceDefinition;
import cn.cerestech.middleware.balance.entity.Account;
import cn.cerestech.middleware.balance.entity.AccountLog;
import cn.cerestech.middleware.balance.entity.AccountRecord;
import cn.cerestech.middleware.balance.entity.BufferedTransaction;
import cn.cerestech.middleware.balance.errorcode.BalanceErrorCodes;

@Service
public class BalanceService {

	@Autowired
	AccountDao accountDao;

	@Autowired
	AccountRecordDao accountRecordDao;

	@Autowired
	AccountLogDao accountLogDao;

	public List<Account> findByOwner(Owner owner) {
		if (owner == null || owner.isEmpty()) {
			return Lists.newArrayList();
		}
		List<Account> retList = AbstractBalanceConfig.getDefinitions().stream().map(def -> create(owner, def.getType()))
				.collect(Collectors.toList());

		return retList;
	}

	/**
	 * 为owner创建账户(如果没有创建的话）
	 * 
	 * @param owner
	 * @param type
	 * @return
	 */
	public Account create(Owner owner, DescribableEnum type) {
		Account acc = accountDao.findByOwnerAndType(owner, type.key());
		if (acc == null) {
			acc = new Account();
			acc.setAmount(BigDecimal.ZERO);
			acc.setFreeze(BigDecimal.ZERO);
			acc.setType(type.key());
			acc.setOwner(owner);
			accountDao.save(acc);
		}
		return acc;
	}

	/**
	 * 向账户中注入金额。为正数就是增加，负数则是减少，0则不变动
	 * 
	 * @param owner
	 * @param type
	 * @param amount
	 * @return
	 */
	@Transactional
	public Result<Account> change(Owner owner, DescribableEnum type, BigDecimal amount, Extra extra) {
		Account acc = create(owner, type);
		if (acc == null) {
			return Result.error(BalanceErrorCodes.ACCOUNT_NOT_FOUND);
		}
		BalanceDefinition def = AbstractBalanceConfig.get(type.key());
		if (amount == null || amount.equals(BigDecimal.ZERO)) {
			return Result.success(acc);
		}

		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal oldAmount = acc.getAmount();
			BigDecimal newAmount = oldAmount.add(amount);
			acc.setAmount(newAmount);// 加入账户余额
			accountDao.save(acc);
			// 增加金额、创建获得记录
			AccountRecord record = new AccountRecord();
			record.setAmount(amount);
			record.setLeftAmount(amount);
			record.setExpiredTime(new Date(System.currentTimeMillis() + def.getExpiredTime()));
			record.setAccount(acc);

			// 如果余额为负,要从本次获得记录中扣除
			if (oldAmount.compareTo(BigDecimal.ZERO) < 0) {
				if (oldAmount.add(amount).compareTo(BigDecimal.ZERO) <= 0) {
					// 本次获得记录全部扣减完毕
					record.setLeftAmount(BigDecimal.ZERO);
				} else {
					// 还有剩余获得记录
					record.setLeftAmount(newAmount);
				}
			}

			accountRecordDao.save(record);

			// 记录账户变动记录
			log(acc, amount, oldAmount, newAmount, extra);
		} else {
			// 账户禁止转出
			if (acc.isFreezeOut()) {
				return Result.error(BalanceErrorCodes.ACCOUNT_OUT_BANNED);
			}
			// 账户余额不足 并且不允许为负
			if (acc.getAmount().compareTo(amount.abs()) < 0 && !def.getAllowNegative()) {
				return Result.error(BalanceErrorCodes.AMOUNT_NOT_ENOUGH);
			}
			// 扣减账户
			BigDecimal oldAmount = acc.getAmount();
			BigDecimal newAmount = oldAmount.add(amount);
			acc.setAmount(newAmount);// 加入账户余额
			accountDao.save(acc);

			// 开始扣减金额，要响应扣除获得记录
			List<AccountRecord> recordList = accountRecordDao
					.findByAccountIdAndLeftAmountGreaterThanOrderByCreateTime(acc.getId(), BigDecimal.ZERO);
			// 开始逐步扣减抵扣金额
			BigDecimal left = amount.abs();
			for (AccountRecord rec : recordList) {
				if (left.compareTo(rec.getLeftAmount()) >= 0) {
					// 还有多的
					left = left.subtract(rec.getLeftAmount());
					rec.setLeftAmount(BigDecimal.ZERO);
				} else {
					// 本笔扣完
					rec.setLeftAmount(rec.getLeftAmount().subtract(left));
					left = BigDecimal.ZERO;
					break;
				}
				accountRecordDao.save(rec);
			}
			// 记录账户变动记录
			log(acc, amount, oldAmount, newAmount, extra);
		}
		return Result.success(acc);

	}

	public void log(Account acc, BigDecimal amount, BigDecimal oldAmount, BigDecimal newAmount, Extra extra) {
		AccountLog log = new AccountLog();
		log.setAmount(amount);
		log.setAmountNew(newAmount);
		log.setAmountOld(oldAmount);
		log.setExtra(extra);
		log.setAccount(acc);
		accountLogDao.save(log);
	}

	@Transactional
	public Result<AccountRecord> makeRecordExpired(Long recordId) {
		if (recordId == null) {
			return Result.error(BalanceErrorCodes.ACCOUNT_RECORD_NOT_FOUND);
		}
		AccountRecord record = accountRecordDao.findOne(recordId);
		if (record == null) {
			return Result.error(BalanceErrorCodes.ACCOUNT_RECORD_NOT_FOUND);
		}

		if (record.getLeftAmount().compareTo(BigDecimal.ZERO) == 0) {
			// 金额已经消费完毕，无需过期
			return Result.success(record);
		}

		BigDecimal left = record.getLeftAmount();
		record.setLeftAmount(BigDecimal.ZERO);
		accountRecordDao.save(record);

		Account acc = record.getAccount();
		BigDecimal oldAmount = acc.getAmount();
		BigDecimal newAmount = oldAmount.subtract(left);
		acc.setAmount(newAmount);// 扣减掉过期金额
		accountDao.save(acc);

		// 记录过期
		Extra extra = new Extra();
		extra.setRemark("过期失效");

		log(acc, left, oldAmount, newAmount, extra);
		return Result.success(record);
	}

	public Result<BufferedTransaction> bufferedTransaction(Owner toWhom, DescribableEnum toType,
			DescribableEnum toReason, BigDecimal amount, Extra extra) {
		return null;
	}
}
