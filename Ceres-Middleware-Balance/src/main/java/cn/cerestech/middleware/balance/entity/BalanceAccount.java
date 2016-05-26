package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.framework.support.persistence.Owner;

/**
 * 账户下的子账户<br/>
 * 子账户只对应一种金额
 * 
 * @author harryhe
 *
 */
@Entity
@Table(name = "$$balance_account")
public class BalanceAccount extends IdEntity {

	/**
	 * 账户当前余额
	 */
	@Column()
	private BigDecimal amount = BigDecimal.ZERO;

	/**
	 * 账户当前冻结金额
	 */
	@Column()
	public BigDecimal amountFreeze = BigDecimal.ZERO;

	/**
	 * 所有者类型
	 */
	@Column()
	public String ownerType;

	/**
	 * 所有者ID
	 */
	@Column()
	public Long ownerId;

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
		this.setOwnerType(owner.getOwnerType());
		this.setOwnerId(owner.getOwnerId());
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Owner getOwner() {
		return new Owner(this.getOwnerType(), this.getOwnerId());
	}

	public BigDecimal getAmountFreeze() {
		return amountFreeze;
	}

	public void setAmountFreeze(BigDecimal amountFreeze) {
		this.amountFreeze = amountFreeze;
	}

	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
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
