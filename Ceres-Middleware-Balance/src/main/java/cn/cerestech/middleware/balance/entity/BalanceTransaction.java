package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.middleware.balance.enums.ActionType;
import cn.cerestech.middleware.balance.enums.TransactionStatus;

@Entity
@Table(name = "$$balance_transaction")
public class BalanceTransaction extends IdEntity {

	/**
	 * 交易状态
	 */
	@Column()
	private String status = TransactionStatus.PROCESSING.key();

	/**
	 * 业务状态
	 */
	@Column()
	private String businessStatusKey;
	/**
	 * 业务状态描述
	 */
	@Column()
	private String businessStatusDesc;
	/**
	 * 交易原因
	 */
	@Column()
	private String reason;

	/**
	 * 备注
	 */
	@Column()
	private String remark;

	/**
	 * 扩展1
	 */
	@Column()
	private String extra1;
	/**
	 * 扩展2
	 */
	@Column()
	private String extra2;

	/**
	 * 扩展3
	 */
	@Column()
	private String extra3;
	/**
	 * 扩展4
	 */
	@Column()
	private String extra4;
	/**
	 * 扩展5
	 */
	@Column()
	private String extra5;

	/**
	 * 交易完成时间
	 */
	@Column()
	private Date finish_time;

	private List<Log> logs = Lists.newArrayList();

	public static BalanceTransaction init(DescribableEnum business_status, DescribableEnum reason, String remark,
			String... extra) {
		BalanceTransaction trans = new BalanceTransaction();
		if (business_status != null) {
			trans.setBusinessStatusKey(business_status.key());
			trans.setBusinessStatusDesc(business_status.desc());
		}
		if (reason != null) {
			trans.setReason(reason.key());
		}
		for (int i = 0; i < extra.length; i++) {
			String str = extra[i];
			switch (i) {
			case 0:
				trans.setExtra1(str);
				break;
			case 1:
				trans.setExtra2(str);
				break;
			case 2:
				trans.setExtra3(str);
				break;
			case 3:
				trans.setExtra4(str);
				break;
			case 4:
				trans.setExtra5(str);
				break;
			}
		}

		trans.setRemark(remark);

		return trans;
	}

	public BalanceTransaction add(DescribableEnum balanceType, Owner toWhom, BigDecimal amount) {
		log(balanceType, toWhom, ActionType.IN, amount, null, null, null);
		return this;
	}

	/**
	 * 多出来的参数如果为null,则表示不修改默认值
	 * 
	 * @param toWhom
	 * @param amount
	 * @param business_stats
	 * @param reason
	 * @param remark
	 * @param extra
	 * @return
	 */
	public BalanceTransaction add(DescribableEnum balanceType, Owner toWhom, BigDecimal amount,
			DescribableEnum business_stats, DescribableEnum reason, String remark) {
		log(balanceType, toWhom, ActionType.IN, amount, business_stats, reason, remark);
		return this;
	}

	public BalanceTransaction substract(DescribableEnum balanceType, Owner toWhom, BigDecimal amount) {
		log(balanceType, toWhom, ActionType.OUT, amount, null, null, null);
		return this;
	}

	/**
	 * 扣减以后账户保留的最小余额，否则返回余额不足错误
	 * 
	 * @param toWhom
	 * @param amount
	 * @param minimalAmount
	 * @return
	 */
	public BalanceTransaction substract(DescribableEnum balanceType, Owner toWhom, BigDecimal amount,
			BigDecimal minimalAmount) {
		Log log = log(balanceType, toWhom, ActionType.OUT, amount, null, null, null);
//		log.setMinimalAmount(minimalAmount);
		return this;

	}

	public BalanceTransaction substract(DescribableEnum balanceType, Owner toWhom, BigDecimal amount,
			DescribableEnum business_stats, DescribableEnum reason, String remark) {
		log(balanceType, toWhom, ActionType.OUT, amount, business_stats, reason, remark);
		return this;
	}

	public BalanceTransaction substract(DescribableEnum balanceType, Owner toWhom, BigDecimal amount,
			BigDecimal minimalAmount, DescribableEnum business_stats, DescribableEnum reason, String remark) {
		Log log = log(balanceType, toWhom, ActionType.OUT, amount, business_stats, reason, remark);
//		log.setMinimalAmount(minimalAmount);
		return this;
	}

	private Log log(DescribableEnum balanceType, Owner toWhom, ActionType inout, BigDecimal amount,
			DescribableEnum business_stats, DescribableEnum reason, String remark) {
		if (toWhom == null || toWhom.isEmpty() || amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
			throw new IllegalArgumentException("Balance Transaction: arguments is null.");
		}

		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Balance Transaction: [" + amount.toString() + "] can not be negtive.");
		}

		Log log = new Log();
//		log.setActionType(inout.key());
//		log.setAmount(amount);
//		log.setOwner_id(toWhom.getId());
//		log.setOwner_type(toWhom.getType());
//		log.setReason(reason == null ? this.reason : reason.key());
//		log.setBusiness_status_desc(business_stats == null ? businessStatusDesc : business_stats.desc());
//		log.setBusiness_status_key(business_stats == null ? businessStatusKey : business_stats.key());
//		log.setRemark(Strings.isNullOrEmpty(remark) ? this.remark : remark);
//		log.setBalance_type(balanceType.key());
//		log.setBalance_desc(balanceType.desc());
//		log.setExtra1(extra1);
//		log.setExtra2(extra2);
//		log.setExtra3(extra3);
//		log.setExtra4(extra4);
//		log.setExtra5(extra5);
		logs.add(log);
		return log;
	}

	public String getBusinessStatusKey() {
		return businessStatusKey;
	}

	public void setBusinessStatusKey(String businessStatusKey) {
		this.businessStatusKey = businessStatusKey;
	}

	public String getBusinessStatusDesc() {
		return businessStatusDesc;
	}

	public void setBusinessStatusDesc(String businessStatusDesc) {
		this.businessStatusDesc = businessStatusDesc;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getFinish_time() {
		return finish_time;
	}

	public void setFinish_time(Date finish_time) {
		this.finish_time = finish_time;
	}

}
