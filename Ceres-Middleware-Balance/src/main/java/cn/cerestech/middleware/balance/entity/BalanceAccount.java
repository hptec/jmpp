package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.framework.support.persistence.entity.IdEntity;

/**
 * 账户下的子账户<br/>
 * 子账户只对应一种金额
 * 
 * @author harryhe
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "$$balance_account")
public class BalanceAccount extends IdEntity {

	/**
	 * 账户当前余额
	 */
	private BigDecimal amount = BigDecimal.ZERO;

	/**
	 * 账户当前冻结金额
	 */
	private BigDecimal amountFreeze = BigDecimal.ZERO;

	/**
	 * 所有者
	 */
	@Embedded
	private Owner owner;

	/**
	 * 账户类型
	 */
	@Column()
	public String balanceType;

	/**
	 * 账户描述
	 */
	@Column()
	public String balanceDesc;

	public BalanceAccount() {
	}

	public BalanceAccount(Owner owner) {
		this.owner = owner;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public BigDecimal getAmountFreeze() {
		return amountFreeze;
	}

	public void setAmountFreeze(BigDecimal amountFreeze) {
		this.amountFreeze = amountFreeze;
	}

	public String getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}

	public String getBalanceDesc() {
		return balanceDesc;
	}

	public void setBalanceDesc(String balanceDesc) {
		this.balanceDesc = balanceDesc;
	}

}
