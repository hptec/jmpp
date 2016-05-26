package cn.cerestech.middleware.balance.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.framework.support.persistence.Owner;

@SuppressWarnings("serial")
@Entity
@Table(name = "$$balance_bankcard")
public class BankCard extends IdEntity {

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "ownerType", column = @Column(name = "owner_type")),
			@AttributeOverride(name = "ownerId", column = @Column(name = "owner_id")) })
	private Owner owner;

	/**
	 * 所有者类型
	 */
	@Column()
	public String ownerType;

	/**
	 * 所有者ID
	 */
	@Column()
	public Long ownerId;

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

	@Column(title = "是否冻结")
	public String frozen = YesNo.NO.key();

	public void setOwner(Owner owner) {
		this.owner_id = owner.getOwner_id();
		this.owner_type = owner.getOwner_type();
	}

	public String getOwner_type() {
		return owner_type;
	}

	public String getFrozen() {
		return frozen;
	}

	public void setFrozen(String frozen) {
		this.frozen = frozen;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getProvince_code() {
		return province_code;
	}

	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_branch() {
		return bank_branch;
	}

	public void setBank_branch(String bank_branch) {
		this.bank_branch = bank_branch;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSec_code() {
		return sec_code;
	}

	public void setSec_code(String sec_code) {
		this.sec_code = sec_code;
	}

	public String getCard_no_confirm() {
		return card_no_confirm;
	}

	public void setCard_no_confirm(String card_no_confirm) {
		this.card_no_confirm = card_no_confirm;
	}

}