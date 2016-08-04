package cn.cerestech.middleware.balance.monitor;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

import cn.cerestech.middleware.balance.entity.Withdraw;

/**
 * 用于处理微信账户提现的后台程序
 * 
 * @author harryhe
 *
 */
// @Service
public class BalanceWithdrawMPMonitor {

	public Supplier<List<Withdraw>> getReloader() {
		return () -> {
			// List<Withdraw> retList = mysqlService.queryBy(Withdraw.class,
			// " state = '" + WithdrawState.APPROVED.key() + "' AND channel='" +
			// WithdrawChannel.MP.key() + "'");
			// return retList;
			return Lists.newArrayList();
		};
	}

	public Function<Withdraw, Boolean> getMonitor() {
		return wd -> {
			// TODO 处理每一个提现请求，都是微信提现请求
			return Boolean.FALSE;
		};
	}

	public Boolean isRunable() {
		return null;
//		if (getLastRunTime() == null) {
//			return Boolean.TRUE;
//		} else {
//			return System.currentTimeMillis() > (getLastRunTime().getTime() + 5 * 1000L);
//		}
	}

}
