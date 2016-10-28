package cn.cerestech.framework.support.persistence.entity;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.cerestech.framework.support.persistence.Owner;

@Embeddable
public class ActBy {

	@Embedded
	private Owner byWhom;

	@Temporal(TemporalType.TIMESTAMP)
	private Date byTime;

	public Owner getByWhom() {
		return byWhom;
	}

	public void setByWhom(Owner byWhom) {
		this.byWhom = byWhom;
	}

	public Date getByTime() {
		return byTime;
	}

	public void setByTime(Date byTime) {
		this.byTime = byTime;
	}

}
