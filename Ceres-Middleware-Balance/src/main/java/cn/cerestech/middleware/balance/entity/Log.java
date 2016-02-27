package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;
import java.util.Date;

import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.framework.persistence.enums.ColumnDataType;
import cn.cerestech.middleware.balance.enums.ActionType;
import cn.cerestech.middleware.balance.enums.TransactionStatus;

@SuppressWarnings("serial")
@Table(value = "$$balance_log", comment = "账户变动记录")
public class Log extends BaseEntity {

	@Column(title = "交易ID")
	public Long transaction_id;

	@Column(title = "所有者类型")
	public String owner_type;

	@Column(title = "所有者ID")
	public Long owner_id;

	@Column(title = "账户类型")
	public String balance_type;

	@Column(title = "账户描述")
	public String balance_desc;

	@Column(title = "出入账")
	public String actionType;

	@Column(defaultValue = "0.00", title = "期初")
	public BigDecimal amount_openning = BigDecimal.ZERO;

	@Column(defaultValue = "0.00", title = "入账")
	public BigDecimal amount = BigDecimal.ZERO;

	@Column(defaultValue = "0.00", title = "出账")
	public BigDecimal amount_closing = BigDecimal.ZERO;

	@Column(title = "业务状态")
	public String business_status_key;
	@Column(title = "业务状态描述")
	public String business_status_desc;

	@Column(title = "交易状态")
	public String status = TransactionStatus.PROCESSING.key();

	@Column(title = "变动类型")
	public String reason;

	@Column(title = "时间")
	public Date log_time = new Date();

	@Column(title = "备注", type = ColumnDataType.TEXT)
	public String remark;

	@Column(title = "冻结交易号")
	public Long freeze_id;

	@Column(title = "扩展1")
	public String extra1;
	@Column(title = "扩展2")
	public String extra2;
	@Column(title = "扩展3")
	public String extra3;
	@Column(title = "扩展4")
	public String extra4;
	@Column(title = "扩展5")
	public String extra5;

	@Column(title = "最小额度限制")
	public BigDecimal minimalAmount;// 执行操作以后的最小限制额度

	@Column(title = "入账时间")
	public Date account_time;

	public Owner getOwner() {
		return new Owner(getOwner_type(), getOwner_id());
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

	public BigDecimal getAmount_openning() {
		return amount_openning;
	}

	public void setAmount_openning(BigDecimal amount_openning) {
		this.amount_openning = amount_openning;
	}

	public BigDecimal getAmount_closing() {
		return amount_closing;
	}

	public void setAmount_closing(BigDecimal amount_closing) {
		this.amount_closing = amount_closing;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getLog_time() {
		return log_time;
	}

	public void setLog_time(Date log_time) {
		this.log_time = log_time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBusiness_status_key() {
		return business_status_key;
	}

	public void setBusiness_status_key(String business_status_key) {
		this.business_status_key = business_status_key;
	}

	public String getBusiness_status_desc() {
		return business_status_desc;
	}

	public void setBusiness_status_desc(String business_status_desc) {
		this.business_status_desc = business_status_desc;
	}

	public BigDecimal getMinimalAmount() {
		return minimalAmount;
	}

	public void setMinimalAmount(BigDecimal minimalAmount) {
		this.minimalAmount = minimalAmount;
	}

	public Date getAccount_time() {
		return account_time;
	}

	public void setAccount_time(Date account_time) {
		this.account_time = account_time;
	}

	public String getExtra1() {
		return extra1;
	}

	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}

	public String getExtra2() {
		return extra2;
	}

	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}

	public String getExtra3() {
		return extra3;
	}

	public void setExtra3(String extra3) {
		this.extra3 = extra3;
	}

	public String getExtra4() {
		return extra4;
	}

	public void setExtra4(String extra4) {
		this.extra4 = extra4;
	}

	public String getExtra5() {
		return extra5;
	}

	public void setExtra5(String extra5) {
		this.extra5 = extra5;
	}

	public Long getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(Long transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Boolean actionEquals(ActionType type) {
		return EnumCollector.forClass(ActionType.class).keyOf(this.getActionType()).equals(type);
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

	public Long getFreeze_id() {
		return freeze_id;
	}

	public void setFreeze_id(Long freeze_id) {
		this.freeze_id = freeze_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
