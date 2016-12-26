package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.cerestech.framework.core.json.JsonIgnore;
import cn.cerestech.framework.support.persistence.entity.Extra;
import cn.cerestech.framework.support.persistence.entity.IdEntity;
import cn.cerestech.middleware.balance.enums.FreezeState;

@SuppressWarnings("serial")
@Entity
@Table(name = "$$sys_balance_account_freeze")
public class Freeze extends IdEntity {

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id")
	@JsonIgnore
	private Account account;
	
	// 冻结金额
	@Column(precision = 14, scale = 2)
	private BigDecimal amount = BigDecimal.ZERO;

	// 冻结时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date freezeTime = new Date();

	// 冻结原因
	@Embedded
	private Extra freezeExtra;

	// 解冻时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date unfreezeTime;

	// 解冻原因
	private Extra unfreezeExtra;

	// 状态
	public FreezeState state;

	public BigDecimal getAmount() {
		return amount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getFreezeTime() {
		return freezeTime;
	}

	public void setFreezeTime(Date freezeTime) {
		this.freezeTime = freezeTime;
	}

	public Extra getFreezeExtra() {
		return freezeExtra;
	}

	public void setFreezeExtra(Extra freezeExtra) {
		this.freezeExtra = freezeExtra;
	}

	public Date getUnfreezeTime() {
		return unfreezeTime;
	}

	public void setUnfreezeTime(Date unfreezeTime) {
		this.unfreezeTime = unfreezeTime;
	}

	public Extra getUnfreezeExtra() {
		return unfreezeExtra;
	}

	public void setUnfreezeExtra(Extra unfreezeExtra) {
		this.unfreezeExtra = unfreezeExtra;
	}

	public FreezeState getState() {
		return state;
	}

	public void setState(FreezeState state) {
		this.state = state;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
