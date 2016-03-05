package cn.cerestech.middleware.balance.criteria;

import java.util.Date;

import cn.cerestech.framework.core.search.AbstractCriteria;
import cn.cerestech.middleware.balance.entity.Owner;
import cn.cerestech.middleware.balance.entity.Withdraw;

public class WithdrawCriteria<T extends Withdraw> extends AbstractCriteria<T> {

	private Long owner_id;
	private String owner_type;

	private String state;
	private String channel;

	private String keyword;

	private Date dateFrom;
	private Date dateTo;

	public WithdrawCriteria<T> setOwner(Owner owner) {
		if (owner != null) {
			owner_id = owner.getOwner_id();
			owner_type = owner.getOwner_type();
		}
		return this;
	}

	public Long getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Long owner_id) {
		this.owner_id = owner_id;
	}

	public String getOwner_type() {
		return owner_type;
	}

	public void setOwner_type(String owner_type) {
		this.owner_type = owner_type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

}
