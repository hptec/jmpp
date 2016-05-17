package cn.cerestech.framework.core.date;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 单个时间段
 * 
 * @author harryhe
 *
 */
public class TimeRange {

	private Calendar from = Calendar.getInstance();
	private Calendar to = Calendar.getInstance();

	/**
	 * 在计算相差值时，不足一个时间单位，是截断还是差值增加1
	 */
	private Boolean roundUp = Boolean.FALSE;

	public static TimeRange beginOf(Calendar calc) {
		TimeRange tr = new TimeRange();
		tr.from = calc == null ? Calendar.getInstance() : calc;
		return tr;
	}

	public static TimeRange beginOf(Date date) {
		TimeRange tr = new TimeRange();
		tr.from = Calendar.getInstance();
		tr.from.setTime(date == null ? new Date() : date);
		return tr;
	}

	public static TimeRange beginOf(Long inMillis) {
		TimeRange tr = new TimeRange();
		tr.from = Calendar.getInstance();
		tr.from.setTimeInMillis(inMillis == null ? System.currentTimeMillis() : inMillis);
		return tr;
	}

	public static TimeRange endOf(Calendar calc) {
		TimeRange tr = new TimeRange();
		tr.to = calc == null ? Calendar.getInstance() : calc;
		return tr;
	}

	public static TimeRange endOf(Date date) {
		TimeRange tr = new TimeRange();
		tr.to = Calendar.getInstance();
		tr.to.setTime(date == null ? new Date() : date);
		return tr;
	}

	public static TimeRange from(Calendar from, Calendar to) {
		return TimeRange.beginOf(from).ending(to);
	}

	public static TimeRange from(Date from, Date to) {
		return TimeRange.beginOf(from).ending(to);
	}

	public static TimeRange from(Long from, Long to) {
		return TimeRange.beginOf(from).ending(to);
	}

	public TimeRange beginning(Calendar calc) {
		from = calc == null ? Calendar.getInstance() : calc;
		return this;
	}

	public TimeRange beginning(Date date) {
		from = Calendar.getInstance();
		from.setTime(date == null ? new Date() : date);
		return this;
	}

	public TimeRange beginning(Long inMillis) {
		from = Calendar.getInstance();
		from.setTimeInMillis(inMillis == null ? System.currentTimeMillis() : inMillis);
		return this;
	}

	public TimeRange ending(Calendar calc) {
		to = calc == null ? Calendar.getInstance() : calc;
		return this;
	}

	public TimeRange ending(Date date) {
		to = Calendar.getInstance();
		to.setTime(date == null ? new Date() : date);
		return this;
	}

	public TimeRange ending(Long inMillis) {
		to = Calendar.getInstance();
		from.setTimeInMillis(inMillis == null ? System.currentTimeMillis() : inMillis);
		return this;
	}

	public Boolean inRange(Calendar calc) {
		if (calc.before(from)) {
			return Boolean.FALSE;
		} else if (calc.after(to)) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}

	/**
	 * 在计算日期段差值的时候，有余数的情况下是否要增加一个周期(默认为不增加)
	 * 
	 * @return
	 */
	public TimeRange roundUp() {
		roundUp = Boolean.TRUE;
		return this;
	}

	public Long diff(TimeUnit unit) {
		return Moment.from(from.getTime()).diff(to.getTime(), unit);
	}

	/**
	 * 获取时间段内相差的差值
	 * 
	 * @return
	 */
	public Integer days() {
		return diff(TimeUnit.DAYS).intValue();
	}

	public Integer diffHours() {
		return diff(TimeUnit.HOURS).intValue();
	}

	public Long diffMinutes() {
		return diff(TimeUnit.MINUTES);
	}

	public Long diffSeconds() {
		return diff(TimeUnit.SECONDS);
	}

	public Long diffMillis() {
		return diff(TimeUnit.MILLISECONDS);
	}

	public static void main(String argus[]) {
		Moment d1 = Moment.fromDateTime("2016-5-15 00:00:00");
		Moment d2 = Moment.fromDateTime("2016-5-16 00:00:01");
		Long diff = TimeRange.from(d1.getCalendar(), d2.getCalendar()).diff(TimeUnit.MINUTES);
		System.out.println("相差: " + diff);
	}
}
