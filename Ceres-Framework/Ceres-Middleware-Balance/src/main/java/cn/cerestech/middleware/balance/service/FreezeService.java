package cn.cerestech.middleware.balance.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.framework.support.persistence.entity.Extra;
import cn.cerestech.middleware.balance.dao.AccountDao;
import cn.cerestech.middleware.balance.dao.FreezeDao;
import cn.cerestech.middleware.balance.entity.Account;
import cn.cerestech.middleware.balance.entity.Freeze;
import cn.cerestech.middleware.balance.enums.FreezeState;
import cn.cerestech.middleware.balance.errorcode.BalanceErrorCodes;

@Service
public class FreezeService {
	
	@Autowired
	FreezeDao freezeDao;
	
	@Autowired
	AccountDao accountDao;
	
	@Autowired
	BalanceService balanceService;
	
	/**
	 * 冻结
	 * @param owner
	 * @param type
	 * @param freezeAmount
	 * @param freezeExtra
	 * @return
	 */
	@Transactional
	public Result<Freeze> freeze(Owner owner, DescribableEnum type,BigDecimal freezeAmount,Extra freezeExtra){
		Account acc = balanceService.findByOwner(owner, type);
		if (acc == null) {
			return Result.error(BalanceErrorCodes.ACCOUNT_NOT_FOUND);
		}
		
		//检查可用额度是否足够
		if(acc.getEffectiveAmount().compareTo(freezeAmount) < 0){
			return Result.error(BalanceErrorCodes.FREEZE_ERROR_BY_EFFECTIVE_AMOUNT_LESS);
		}
		Freeze freeze = new Freeze();
		freeze.setAccount(acc);
		freeze.setAmount(freezeAmount);
		freeze.setFreezeExtra(freezeExtra);
		freeze.setFreezeTime(new Date());
		freeze.setState(FreezeState.FREEZING);
		freezeDao.save(freeze);
		
		BigDecimal newFreeze = acc.getFreeze().add(freezeAmount);
		acc.setFreeze(newFreeze);
		accountDao.save(acc);
		
		return Result.success().setObject(freeze);
	}
	
	/**
	 * 解冻
	 * @param freezeId
	 * @param unfreezeExtra
	 * @return
	 */
	public Result<Freeze> unFreeze(Long freezeId,Extra unfreezeExtra){
		Freeze freeze = freezeDao.findOne(freezeId);
		if(freeze == null){
			return Result.error(BalanceErrorCodes.FREEZE_NOT_FOUND);
		}
		
		freeze.setState(FreezeState.UNFREEZE);
		freeze.setUnfreezeExtra(unfreezeExtra);
		freeze.setUnfreezeTime(new Date());
		freezeDao.save(freeze);
		
		Account acc = freeze.getAccount();
		BigDecimal newFreeze = acc.getFreeze().subtract(freeze.getAmount());
		acc.setFreeze(newFreeze);
		accountDao.save(acc);
		
		return Result.success().setObject(freeze);
	}
}
