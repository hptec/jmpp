package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;

import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;

/**
 * 账户下的子账户
 * 
 * @author harryhe
 *
 */
@SuppressWarnings("serial")
@Table("$$balance_account")
public class BalanceAccount extends BaseEntity {

	@Column(title = "余额")
	public BigDecimal amount = BigDecimal.ZERO;

	@Column(title = "冻结金额")
	public BigDecimal amount_freeze = BigDecimal.ZERO;

	@Column(title = "历史总计")
	public BigDecimal amount_history_all = BigDecimal.ZERO;

	@Column(title = "所有者类型")
	public String owner_type;

	@Column(title = "所有者ID")
	public Long owner_id;

	@Column(title = "账户类型")
	public String balance_type;

	@Column(title = "账户描述")
	public String balance_desc;

	public BalanceAccount() {
	}

	public BalanceAccount(Owner owner) {
		this.setOwner_type(owner.getOwner_type());
		this.setOwner_id(owner.getOwner_id());
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmount_freeze() {
		return amount_freeze;
	}

	public void setAmount_freeze(BigDecimal amount_freeze) {
		this.amount_freeze = amount_freeze;
	}

	public BigDecimal getAmount_history_all() {
		return amount_history_all;
	}

	public void setAmount_history_all(BigDecimal amount_history_all) {
		this.amount_history_all = amount_history_all;
	}

	public String getOwner_type() {
		return owner_type;
	}

	public void setOwner_type(String owner_type) {
		this.owner_type = owner_type;
	}

	public Long getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Long owner_id) {
		this.owner_id = owner_id;
	}

	public String getBalance_type() {
		return balance_type;
	}

	public void setBalance_type(String balance_type) {
		this.balance_type = balance_type;
	}

	public String getBalance_desc() {
		return balance_desc;
	}

	public void setBalance_desc(String balance_desc) {
		this.balance_desc = balance_desc;
	}
}
