package cn.cerestech.middleware.balance.criteria;

import cn.cerestech.framework.core.search.AbstractCriteria;
import cn.cerestech.middleware.balance.entity.Log;
import cn.cerestech.middleware.balance.entity.Owner;

public class LogCriteria<T extends Log> extends AbstractCriteria<T> {

	private Long owner_id;
	private String owner_type;

	private String balance_type;
	private String action_type;
	private String status;
	private String fromDate;
	private String toDate;

	public LogCriteria<T> setOwner(Owner owner) {
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

	public String getAction_type() {
		return action_type;
	}

	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getBalance_type() {
		return balance_type;
	}

	public void setBalance_type(String balance_type) {
		this.balance_type = balance_type;
	}

	
}
