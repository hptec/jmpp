package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.framework.support.persistence.entity.IdEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "$$balance_freeze")
public class Freeze extends IdEntity {

	// 冻结金额
	public BigDecimal amount = BigDecimal.ZERO;

	// 所有者类型
	@Embedded
	private Owner owner;

	// 账户类型
	public String balance_type;

	// 账户描述
	public String balance_desc;

	// 冻结时间
	public Date freeze_time = new Date();

	// 冻结原因
	public String freeze_reason;
	// 冻结备注
	public String freeze_remark;

	// 解冻时间
	public Date unfreeze_time;

	// 解冻原因
	public String unfreeze_reason;
	// 解冻备注
	public String unfreeze_remark;

	// 状态
	public String state;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getFreeze_time() {
		return freeze_time;
	}

	public void setFreeze_time(Date freeze_time) {
		this.freeze_time = freeze_time;
	}

	public String getFreeze_reason() {
		return freeze_reason;
	}

	public void setFreeze_reason(String freeze_reason) {
		this.freeze_reason = freeze_reason;
	}

	public String getFreeze_remark() {
		return freeze_remark;
	}

	public void setFreeze_remark(String freeze_remark) {
		this.freeze_remark = freeze_remark;
	}

	public Date getUnfreeze_time() {
		return unfreeze_time;
	}

	public void setUnfreeze_time(Date unfreeze_time) {
		this.unfreeze_time = unfreeze_time;
	}

	public String getUnfreeze_reason() {
		return unfreeze_reason;
	}

	public void setUnfreeze_reason(String unfreeze_reason) {
		this.unfreeze_reason = unfreeze_reason;
	}

	public String getUnfreeze_remark() {
		return unfreeze_remark;
	}

	public void setUnfreeze_remark(String unfreeze_remark) {
		this.unfreeze_remark = unfreeze_remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
