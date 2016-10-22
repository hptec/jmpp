package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.framework.support.persistence.entity.IdEntity;
import cn.cerestech.middleware.balance.dataobject.Extra;
import cn.cerestech.middleware.balance.enums.FreezeState;

@SuppressWarnings("serial")
//@Entity
//@Table(name = "$$balance_freeze")
public class Freeze extends IdEntity {

	// 冻结金额
	@Column(precision = 14, scale = 2)
	private BigDecimal amount = BigDecimal.ZERO;

	// 所有者类型
	@Embedded
	private Owner owner;

	// 账户类型
	private String type;

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

}
