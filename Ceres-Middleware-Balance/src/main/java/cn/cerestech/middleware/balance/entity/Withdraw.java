package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;
import java.util.Date;

import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.middleware.balance.enums.WithdrawChannel;
import cn.cerestech.middleware.balance.enums.WithdrawState;

@SuppressWarnings("serial")
@Table(value = "$$balance_withdraw", comment = "账户提现记录")
public class Withdraw extends BaseEntity {

	@Column(title = "提现金额")
	public BigDecimal amount = BigDecimal.ZERO;

	@Column(title = "手续费")
	public BigDecimal fee = BigDecimal.ZERO;

	@Column(title = "实得金额")
	public BigDecimal fact_amount = BigDecimal.ZERO;

	@Column(title = "提现账户类型")
	public String owner_type;

	@Column(title = "提现账户")
	public Long owner_id;

	@Column(title = "交易号")
	public Long trans_id;

	@Column(title = "状态")
	public String state = WithdrawState.SUBMIT.key();

	@Column(title = "备注")
	public String submit_remark;

	@Column(title = "提交时间")
	public Date submit_time = new Date();

	@Column(title = "取消时间")
	public Date cancel_time;

	@Column(title = "取消者")
	public Long cancel_by;
	
	public Long getCancel_by() {
		return cancel_by;
	}

	public void setCancel_by(Long cancel_by) {
		this.cancel_by = cancel_by;
	}

	@Column(title = "审批时间")
	public Date audit_time;

	@Column(title = "审核者")
	public Long audit_by;

	@Column(title = "审核原因")
	public String audit_reason;

	@Column(title = "处理完成时间")
	public Date finish_time;
	@Column(title = "完成者")
	public Long finish_by;

	@Column(title = "提现通道")
	public String channel;

	@Column(title = "户名")
	public String username;

	@Column(title = "联系电话")
	public String phone;

	@Column(title = "第三方交易流水号", length = 70)
	public String third_trade_no;

	@Column(title = "提现到资源ID")
	public String channel_to_resource_id;

	@Column(title = "提现到资源描述")
	public String channel_to_resource_desc;

	public Boolean stateEqual(WithdrawState state) {
		return state.key().equals(getState());
	}

	public String getChannel_to_resource_desc() {
		return channel_to_resource_desc;
	}

	public void setChannel_to_resource_desc(String channel_to_resource_desc) {
		this.channel_to_resource_desc = channel_to_resource_desc;
	}

	public Boolean channelEqual(WithdrawChannel way) {
		return channel.equals(getChannel());
	}

	public Date getCancel_time() {
		return cancel_time;
	}

	public void setCancel_time(Date cancel_time) {
		this.cancel_time = cancel_time;
	}

	public void setOwner(Owner owner) {
		setOwner_id(owner.getOwner_id());
		setOwner_type(owner.getOwner_type());
	}

	public Owner getOwner() {
		return new Owner(getOwner_type(), getOwner_id());
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSubmit_remark() {
		return submit_remark;
	}

	public void setSubmit_remark(String submit_remark) {
		this.submit_remark = submit_remark;
	}

	public Date getSubmit_time() {
		return submit_time;
	}

	public void setSubmit_time(Date submit_time) {
		this.submit_time = submit_time;
	}

	public Date getAudit_time() {
		return audit_time;
	}

	public void setAudit_time(Date audit_time) {
		this.audit_time = audit_time;
	}

	public Date getFinish_time() {
		return finish_time;
	}

	public void setFinish_time(Date finish_time) {
		this.finish_time = finish_time;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getChannel_to_resource_id() {
		return channel_to_resource_id;
	}

	public void setChannel_to_resource_id(String channel_to_resource_id) {
		this.channel_to_resource_id = channel_to_resource_id;
	}

	public String getPhone() {
		return phone;
	}

	public Long getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(Long trans_id) {
		this.trans_id = trans_id;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getThird_trade_no() {
		return third_trade_no;
	}

	public void setThird_trade_no(String third_trade_no) {
		this.third_trade_no = third_trade_no;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getFact_amount() {
		return fact_amount;
	}

	public void setFact_amount(BigDecimal fact_amount) {
		this.fact_amount = fact_amount;
	}

	public Long getAudit_by() {
		return audit_by;
	}

	public void setAudit_by(Long audit_by) {
		this.audit_by = audit_by;
	}

	public String getAudit_reason() {
		return audit_reason;
	}

	public void setAudit_reason(String audit_reason) {
		this.audit_reason = audit_reason;
	}

	public Long getFinish_by() {
		return finish_by;
	}

	public void setFinish_by(Long finish_by) {
		this.finish_by = finish_by;
	}
}
