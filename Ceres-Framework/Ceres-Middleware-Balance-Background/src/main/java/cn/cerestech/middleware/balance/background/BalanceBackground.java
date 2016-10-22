package cn.cerestech.middleware.balance.background;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BalanceBackground {

	@Scheduled
	public void checkRecordExpired() {
		// TODO 检查获得记录是否过期

	}

	@Scheduled
	public void checkFreezeExpired() {
		// TODO 检查冻结记录是否过期
	}
}
