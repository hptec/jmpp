package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.cerestech.framework.core.json.JsonIgnore;
import cn.cerestech.framework.support.persistence.entity.IdEntity;

/**
 * 记录账户每一笔交易获得记录，并用于账户扣减和过期
 * 
 * @author harryhe
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "$$sys_balance_account_record")
public class AccountRecord extends IdEntity {

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	@JsonIgnore
	private Account account;

	// 获得金额
	@Column(precision = 14, scale = 2)
	private BigDecimal amount;

	// 剩余金额
	@Column(precision = 14, scale = 2)
	private BigDecimal leftAmount;

	// 该笔金额过期时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredTime;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(BigDecimal leftAmount) {
		this.leftAmount = leftAmount;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
