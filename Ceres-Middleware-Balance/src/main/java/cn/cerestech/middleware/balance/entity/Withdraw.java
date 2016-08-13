package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.support.persistence.entity.IdEntity;
import cn.cerestech.middleware.balance.enums.WithdrawState;

/**
 * 账户提现记录
 * 
 * @author harryhe
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "$$balance_withdraw")
public class Withdraw extends IdEntity {

	// 提现金额
	public BigDecimal amount = BigDecimal.ZERO;

	// 手续费
	public BigDecimal fee = BigDecimal.ZERO;

	// 实得金额
	public BigDecimal fact_amount = BigDecimal.ZERO;

	// 提现账户类型
	public String owner_type;

	// 提现账户
	public Long owner_id;

	// 交易号
	public Long trans_id;

	// 状态
	public String state = WithdrawState.SUBMIT.key();

	// 备注
	public String submit_remark;

	// 提交时间
	public Date submit_time = new Date();

	// 取消时间
	public Date cancel_time;

	// 取消者
	public Long cancel_by;

	// 审批时间
	public Date audit_time;

	// 审核者
	public Long audit_by;

	// 审核原因
	public String audit_reason;

	// 处理完成时间
	public Date finish_time;
	// 完成者
	public Long finish_by;

	// 提现通道
	public String channel;

	// 户名
	public String username;

	// 联系电话
	public String phone;

	// 第三方交易流水号
	@Column(length = 70)
	public String third_trade_no;

	// 提现到资源ID
	public String channel_to_resource_id;

	// 提现到资源描述
	public String channel_to_resource_desc;
}
