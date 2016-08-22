package cn.cerestech.middleware.balance.service;

import org.springframework.stereotype.Service;

import cn.cerestech.framework.support.configuration.service.ConfigService;

@Service
public class BalanceConfigService extends ConfigService {

//	/**
//	 * 系统配置是否支持银行卡方式的提现
//	 * 
//	 * @return
//	 */
//	public Boolean isWithdrawWayOfBankCardSupport() {
////		return query(BalanceConfigKeys.BALANCE_WITHDRAW_BANKCARD_ENABLED).boolValue();
//	}
//
//	/**
//	 * 系统配置是否支持微信方式的提现
//	 * 
//	 * @return
//	 */
//	public Boolean isWithdrawWayOfMPSupport() {
//		return query(BalanceConfigKeys.BALANCE_WITHDRAW_MP_ENABLED).boolValue();
//	}
//
//	/**
//	 * 系统中允许提现的账户
//	 * 
//	 * @return
//	 */
//	public DescribableEnum withdrawAccountType() {
//		String key = query(BalanceConfigKeys.BALANCE_WITHDRAW_ACCOUNT).stringValue();
//		if (Strings.isNullOrEmpty(key)) {
//			// 需要设置提现账户
//			return null;
//		}
//
//		return EnumCollector.queryEnumByCategory(BalanceService.CATEGORY_KEYWORD).keyOf(key);
//	}
//
//	/**
//	 * 读取提现费率
//	 * 
//	 * @return
//	 */
//	public BigDecimal withdrawFeeRatio() {
//		BigDecimal ratio = query(BalanceConfigKeys.BALANCE_WITHDRAW_FEE_RATIO).decimalValue();
//		return ratio.multiply(new BigDecimal(100));
//	}
//
//	/**
//	 * 设置提现费率
//	 * 
//	 * @return
//	 */
//	public void withdrawFeeRatio(BigDecimal ratio) {
//		ratio = ratio == null ? BigDecimal.ZERO : ratio.divide(new BigDecimal(100));
//		update(BalanceConfigKeys.BALANCE_WITHDRAW_FEE_RATIO, ratio.toString());
//	}
//	/**
//	 * 是否开启短信通知
//	 * @return
//	 */
//	public boolean smsOpen(){
//		return query(BalanceConfigKeys.BALANCE_WITHDRAW_SMS_OPEN).boolValue();
//	}
}
