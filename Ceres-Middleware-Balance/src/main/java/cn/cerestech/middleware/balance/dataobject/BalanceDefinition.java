package cn.cerestech.middleware.balance.dataobject;

import cn.cerestech.framework.core.enums.DescribableEnum;

/**
 * 
 * @author harryhe
 *
 */
public class BalanceDefinition {

	// 账户类型
	private DescribableEnum type;

	// 过期时间，999年
	private Long expiredTime = 999 * 365 * 24 * 60 * 60 * 1000L;

	/**
	 * 是否允许账户为负（默认不允许）
	 */
	private Boolean allowNegative = Boolean.FALSE;

	public DescribableEnum getType() {
		return type;
	}

	public void setType(DescribableEnum type) {
		this.type = type;
	}

	public Long getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Long expiredTime) {
		this.expiredTime = expiredTime;
	}

	public Boolean getAllowNegative() {
		return allowNegative;
	}

	public void setAllowNegative(Boolean allowNegative) {
		this.allowNegative = allowNegative;
	}

}
