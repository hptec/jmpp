package cn.cerestech.middleware.balance.background;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.cerestech.middleware.balance.dao.AccountRecordDao;
import cn.cerestech.middleware.balance.service.BalanceService;

@Component
public class BalanceBackground {
	@Autowired
	BalanceService balanceService;

	@Autowired
	AccountRecordDao accountRecordDao;

	/**
	 * 每3分钟检查一次过期
	 */
	@Scheduled(fixedDelay = 3 * 60 * 1000)
	public void checkRecordExpired() {
		System.out.println("开始检查过期");
		accountRecordDao.findByLeftAmountGreaterThanAndExpiredTimeLessThan(BigDecimal.ZERO, new Date()).forEach(ar -> {
			System.out.println(ar.getId() + " " + new Date());
			balanceService.makeRecordExpired(ar.getId());
		});
		System.out.println("结束检查过期");
	}

//	@Scheduled
	public void checkFreezeExpired() {
		// TODO 检查冻结记录是否过期
	}
}
