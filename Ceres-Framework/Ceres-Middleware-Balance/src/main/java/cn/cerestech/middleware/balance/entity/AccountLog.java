package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.cerestech.framework.core.json.JsonIgnore;
import cn.cerestech.framework.support.persistence.entity.Extra;
import cn.cerestech.framework.support.persistence.entity.IdEntity;

/**
 * 账户流水记录
 * 
 * @author harryhe
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "$$sys_balance_account_log")
public class AccountLog extends IdEntity {

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	@JsonIgnore
	private Account account;

	// 期初
	@Column(precision = 14, scale = 2)
	private BigDecimal amountOld = BigDecimal.ZERO;

	// 入账
	@Column(precision = 14, scale = 2)
	private BigDecimal amount = BigDecimal.ZERO;

	// 出账
	@Column(precision = 14, scale = 2)
	private BigDecimal amountNew = BigDecimal.ZERO;
	// 账户类型
	private String balanceType;

	@Embedded
	private Extra extra;

	public BigDecimal getAmountOld() {
		return amountOld;
	}

	public void setAmountOld(BigDecimal amountOld) {
		this.amountOld = amountOld;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountNew() {
		return amountNew;
	}

	public void setAmountNew(BigDecimal amountNew) {
		this.amountNew = amountNew;
	}

	public Extra getExtra() {
		return extra;
	}

	public void setExtra(Extra extra) {
		this.extra = extra;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}

}
