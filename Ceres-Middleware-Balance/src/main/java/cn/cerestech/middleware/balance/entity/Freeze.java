package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;
import java.util.Date;

import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.framework.support.persistence.Owner;

@SuppressWarnings("serial")
@Table(value = "$$balance_freeze", comment = "账户冻结记录")
public class Freeze extends BaseEntity {

	@Column(title = "冻结金额")
	public BigDecimal amount = BigDecimal.ZERO;

	@Column(title = "所有者类型")
	public String owner_type;

	@Column(title = "所有者ID")
	public Long owner_id;

	@Column(title = "账户类型")
	public String balance_type;

	@Column(title = "账户描述")
	public String balance_desc;

	@Column(title = "冻结时间")
	public Date freeze_time = new Date();

	@Column(title = "冻结原因")
	public String freeze_reason;

	@Column(title = "冻结备注")
	public String freeze_remark;

	@Column(title = "解冻时间")
	public Date unfreeze_time;

	@Column(title = "解冻原因")
	public String unfreeze_reason;

	@Column(title = "解冻备注")
	public String unfreeze_remark;

	@Column(title = "状态")
	public String state;

	public Owner getOwner() {
		return new Owner(getOwner_type(), getOwner_id());
	}

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
