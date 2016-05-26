package cn.cerestech.middleware.balance.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.middleware.balance.enums.ActionType;
import cn.cerestech.middleware.balance.enums.TransactionStatus;

@SuppressWarnings("serial")
@Entity
@Table(name = "$$balance_log")
public class Log extends IdEntity {

	// 交易ID
	public Long transaction_id;

	// 所有者类型
	public String owner_type;

	// 所有者ID
	public Long owner_id;

	// 账户类型
	public String balance_type;

	// 账户描述
	public String balance_desc;

	// 出入账
	public String actionType;

	// 期初
	public BigDecimal amount_openning = BigDecimal.ZERO;

	// 入账
	public BigDecimal amount = BigDecimal.ZERO;

	// 出账
	public BigDecimal amount_closing = BigDecimal.ZERO;

	// 业务状态
	public String business_status_key;
	// 业务状态描述
	public String business_status_desc;

	// 交易状态
	public String status = TransactionStatus.PROCESSING.key();

	// 变动类型
	public String reason;

	// 时间
	public Date log_time = new Date();

	// 备注
	@Type(type = "TEXT")
	public String remark;

	// 冻结交易号
	public Long freeze_id;

	// 扩展1-5
	public String extra1;
	public String extra2;
	public String extra3;
	public String extra4;
	public String extra5;

	// 最小额度限制
	public BigDecimal minimalAmount;// 执行操作以后的最小限制额度

	// 入账时间
	public Date account_time;
}
