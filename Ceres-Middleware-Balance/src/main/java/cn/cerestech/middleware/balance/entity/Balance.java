package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.framework.support.persistence.Owner;

/**
 * 账户余额
 * 
 * @author harryhe
 *
 */
public class Balance extends IdEntity {

	private Map<String, BalanceAccount> pool = Maps.newHashMap();

	private Owner owner;

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public void put(List<BalanceAccount> accounts) {
		if (accounts != null) {
			for (BalanceAccount acc : accounts) {
				pool.put(acc.getBalanceType(), acc);
			}
		}
	}

	public BalanceAccount get(DescribableEnum balanceType) {
		return get(balanceType.key(), balanceType.desc());
	}

	public BalanceAccount get(String key, String desc) {
		BalanceAccount ba = pool.get(key);
		if (ba == null) {
			ba = new BalanceAccount(getOwner());
			ba.setBalanceDesc(key);
			ba.setBalanceType(desc);
			ba.setCreateTime(new Date());
		}
		return ba;
	}

	public BigDecimal getAmount(String key) {
		BalanceAccount ba = get(key, "");
		return ba.getAmount();
	}

	public BigDecimal getAmount(DescribableEnum key) {
		BalanceAccount ba = get(key.key(), key.desc());

		return ba == null ? BigDecimal.ZERO : ba.getAmount();
	}

	public void put(BalanceAccount acc) {
		pool.put(acc.getBalanceType(), acc);
	}
}
