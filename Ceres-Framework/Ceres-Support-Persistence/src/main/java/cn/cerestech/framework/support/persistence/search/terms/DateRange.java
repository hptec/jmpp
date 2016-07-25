package cn.cerestech.framework.support.persistence.search.terms;

import java.util.Date;

import cn.cerestech.framework.core.date.Dates;

public class DateRange {

	private Date startDate;
	private Date endDate;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date beginningOfDay() {
		return isStartNull() ? null : Dates.from(startDate).beginningOfDay().toDate();
	}

	public Date endOfDay() {
		return isEndNull() ? null : Dates.from(endDate).endOfDay().toDate();
	}

	public Boolean isStartNull() {
		return startDate == null;
	}

	public boolean isEndNull() {
		return endDate == null;
	}

}
