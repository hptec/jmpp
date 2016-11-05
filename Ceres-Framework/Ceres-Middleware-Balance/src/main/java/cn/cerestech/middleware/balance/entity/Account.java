package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.framework.support.persistence.entity.IdEntity;

/**
 * 用户账户表
 * 
 * @author harryhe
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "$$sys_balance_account")
public class Account extends IdEntity {

	// 账户余额
	@Column(precision = 14, scale = 2)
	private BigDecimal amount;
	// 冻结金额
	@Column(precision = 14, scale = 2)
	private BigDecimal freeze;

	@Column(length = 35)
	private String type;

	/**
	 * 冻结账户不可转出
	 */
	private YesNo freezeOut = YesNo.NO;

	@Embedded
	private Owner owner;

	/**
	 * 账户是否禁止转出
	 * 
	 * @return
	 */
	public Boolean isFreezeOut() {
		return freezeOut.equals(YesNo.YES);
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFreeze() {
		return freeze;
	}

	public void setFreeze(BigDecimal freeze) {
		this.freeze = freeze;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public YesNo getFreezeOut() {
		return freezeOut;
	}

	public void setFreezeOut(YesNo freezeOut) {
		this.freezeOut = freezeOut;
	}

}
