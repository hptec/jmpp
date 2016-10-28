package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.framework.support.persistence.entity.ActBy;
import cn.cerestech.framework.support.persistence.entity.Extra;
import cn.cerestech.framework.support.persistence.entity.IdEntity;
import cn.cerestech.middleware.balance.enums.BufferedTransState;

/**
 * 延时类交易
 * 
 * @author harryhe
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "$$sys_balance_buffered")
public class BufferedTransaction extends IdEntity {

	/**
	 * 交易金额
	 */
	@Column(precision = 14, scale = 2)
	private BigDecimal amount;

	/**
	 * 交易给谁
	 */
	@Embedded
	private Owner toWhom;

	/**
	 * 交易到哪个账户
	 */
	private String toType;

	/**
	 * 交易原因代码
	 */
	private String toReasonKey;

	/**
	 * 交易原因内容描述
	 */
	private String toReasonDesc;

	/**
	 * 交易状态
	 */
	private BufferedTransState state;

	/**
	 * 取消
	 */
	@Embedded
	private ActBy cancel;

	/**
	 * 确认完成
	 */
	@Embedded
	private ActBy confirm;

	/**
	 * 扩展信息
	 */
	@Embedded
	private Extra extra;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Owner getToWhom() {
		return toWhom;
	}

	public void setToWhom(Owner toWhom) {
		this.toWhom = toWhom;
	}

	public String getToType() {
		return toType;
	}

	public void setToType(String toType) {
		this.toType = toType;
	}

	public String getToReasonKey() {
		return toReasonKey;
	}

	public void setToReasonKey(String toReasonKey) {
		this.toReasonKey = toReasonKey;
	}

	public String getToReasonDesc() {
		return toReasonDesc;
	}

	public void setToReasonDesc(String toReasonDesc) {
		this.toReasonDesc = toReasonDesc;
	}

	public BufferedTransState getState() {
		return state;
	}

	public void setState(BufferedTransState state) {
		this.state = state;
	}

	public ActBy getCancel() {
		return cancel;
	}

	public void setCancel(ActBy cancel) {
		this.cancel = cancel;
	}

	public ActBy getConfirm() {
		return confirm;
	}

	public void setConfirm(ActBy confirm) {
		this.confirm = confirm;
	}

	public Extra getExtra() {
		return extra;
	}

	public void setExtra(Extra extra) {
		this.extra = extra;
	}

}
