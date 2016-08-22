package cn.cerestech.middleware.balance.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.framework.support.persistence.entity.IdEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "$$balance_bankcard")
public class BankCard extends IdEntity {

	@Embedded
	private Owner owner;

	/**
	 * 户名
	 */
	@Column()
	public String ownerName;

	/**
	 * 户号
	 */
	@Column()
	public String cardNo;

	/**
	 * 银行代码
	 */
	@Column()
	public String bankCode;

	/**
	 * 银行名称
	 */
	@Column(length = 100)
	public String bankName;

	/**
	 * 开户支行
	 */
	@Column(length = 100)
	public String bankBranch;

	/**
	 * 开户省份
	 */
	@Column()
	public String province;

	/**
	 * 开户省份代码
	 */
	@Column()
	public String provinceCode;

	/**
	 * 开户城市
	 */
	@Column()
	public String city;

	/**
	 * 开户城市代码
	 */
	@Column()
	public String cityCode;

	/**
	 * 信用卡月
	 */
	@Column()
	public String month;

	/**
	 * 信用卡年
	 */
	@Column()
	public String year;

	/**
	 * 安全码
	 */
	@Column()
	public String secCode;

	/**
	 * 是否删除
	 */
	@Column()
	public String deleted = YesNo.NO.key();

	/**
	 * 是否冻结
	 */
	public String frozen = YesNo.NO.key();

	public String getFrozen() {
		return frozen;
	}

	public void setFrozen(String frozen) {
		this.frozen = frozen;
	}

}