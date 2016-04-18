package cn.cerestech.framework.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Strings;

public class Dates {
	public static final String DATE_TIME_MS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE = "yyyy-MM-dd";
	public static final String TIME_MS = "HH:mm:ss.SSS";
	public static final String TIME = "HH:mm:ss";

	private Calendar calc = Calendar.getInstance();

	public Dates clone() {
		return from(calc.getTimeInMillis());
	}

	public static Dates now() {
		return from(new Date());
	}

	public static Dates beginngOfToday() {
		return from(now().beginningOfDay().toTimeInMillis());
	}

	public static Dates endOfToday() {
		return from(now().endOfDay().toTimeInMillis());
	}

	public static Dates from(Date date) {
		Dates dts = new Dates();
		dts.calc.setTime(date);
		return dts;
	}

	public static Dates from(Long timeInMillis) {
		Dates dts = new Dates();
		dts.calc.setTimeInMillis(timeInMillis);
		return dts;
	}

	public SimpleDateFormat getFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	public Calendar getCalendar() {
		Calendar newCalc = Calendar.getInstance();
		newCalc.setTimeInMillis(calc.getTimeInMillis());
		return newCalc;
	}

	public Date toDate() {
		return getCalendar().getTime();
	}

	public Long toTimeInMillis() {
		return getCalendar().getTimeInMillis();
	}

	/**
	 * 返回时间字符串 yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @param time
	 * @return
	 */
	public String formatDateTimeMs() {
		return getFormat(DATE_TIME_MS).format(calc.getTime());
	}

	/**
	 * 返回时间字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param time
	 * @return
	 */
	public String formatDateTime() {
		return getFormat(DATE_TIME).format(calc.getTime());
	}

	/**
	 * 返回时间字符串 yyyy-MM-dd
	 * 
	 * @param time
	 * @return
	 */
	public String formatDate() {
		return getFormat(DATE).format(calc.getTime());
	}

	public String formatTimeMs() {
		return getFormat(TIME_MS).format(calc.getTime());
	}

	/**
	 * 返回时间字符串 HH:mm:ss
	 * 
	 * @param time
	 * @return
	 */
	public String formatTime() {
		return getFormat(TIME).format(calc.getTime());
	}

	/**
	 * 加上天数，或者减去天数
	 * 
	 * @param time
	 * @param num
	 * @return
	 */
	public Dates addDate(int num) {
		return add(Calendar.DAY_OF_MONTH, num);
	}

	public Dates addYear(int num) {
		return add(Calendar.YEAR, num);
	}

	public Dates addHour(int num) {
		return add(Calendar.HOUR, num);
	}

	public Dates addMinute(int num) {
		return add(Calendar.MINUTE, num);
	}

	public Dates addSecond(int num) {
		return add(Calendar.SECOND, num);
	}

	public Dates addMillisecond(int num) {
		return add(Calendar.MILLISECOND, num);
	}

	public Dates addMonth(int num) {
		return add(Calendar.MONTH, num);
	}

	/**
	 * 修改时间，
	 * 
	 * @param time
	 * @param field
	 *            ： Calendar.DATE etc.
	 * @param num
	 * @return 返回时间对象
	 */
	public Dates add(int field, int num) {
		Dates dts = clone();
		dts.calc.add(field, num);
		return dts;
	}

	/**
	 * 日期的00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public Dates beginningOfDay() {
		Dates dts = clone();
		dts.calc.set(Calendar.HOUR_OF_DAY, 0);
		dts.calc.set(Calendar.MINUTE, 0);
		dts.calc.set(Calendar.SECOND, 0);
		dts.calc.set(Calendar.MILLISECOND, 0);
		return dts;
	}

	/**
	 * 时间的 23：59：59：999
	 * 
	 * @param date
	 * @return
	 */
	public Dates endOfDay() {
		Dates dts = clone();
		dts.calc.set(Calendar.HOUR_OF_DAY, 23);
		dts.calc.set(Calendar.MINUTE, 59);
		dts.calc.set(Calendar.SECOND, 59);
		dts.calc.set(Calendar.MILLISECOND, 999);
		return dts;
	}

	/**
	 * 解析时间字符串
	 * 
	 * @param dateStr
	 * @param format：
	 *            时间字符串格式： 如果为null 或者 blank 则使用 yyyy-MM-dd HH:mm:ss 格式进行解析
	 * @return
	 */
	public static Dates from(String dateStr, String format) {
		SimpleDateFormat sdf = Strings.isNullOrEmpty(format) ? new SimpleDateFormat(DATE_TIME)
				: new SimpleDateFormat(format);
		try {
			Date dt = sdf.parse(dateStr);
			return Dates.from(dt);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解析时间字符串为date yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Dates fromDateTime(String dateStr) {
		return from(dateStr, DATE_TIME);
	}

	/**
	 * 解析时间字符串为date yyyy-MM-dd
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Dates fromDate(String dateStr) {
		return from(dateStr, DATE);
	}

	/**
	 * 解析时间字符串为 HH:mm:ss
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Dates fromTime(String dateStr) {
		return from(dateStr, TIME);
	}

	/**
	 * 解析时间字符串为 yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Dates fromDateTimeMs(String dateStr) {
		return from(dateStr, DATE_TIME_MS);
	}

	/**
	 * 计算两个时间相差的毫秒数
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public Long diff(Date to) {
		if (to == null) {
			throw new IllegalArgumentException("from or to cannot be null");
		}

		return to.getTime() - calc.getTimeInMillis();
	}

	public Long diff(Date to, TimeUnit unit) {
		if (to == null) {
			throw new IllegalArgumentException("from or to cannot be null");
		}

		return diff(to) / unit.toMillis(1);
	}

	/**
	 * 两个日期是否是同一天
	 * 
	 * @return
	 */
	public Boolean isSameDay(Date to) {
		if (to == null) {
			throw new IllegalArgumentException("to cannot be null");
		}
		return formatDate().equals(from(to).formatDate());
	}

	/**
	 * 
	 * @param date
	 * @param field:
	 *            Calendar. field
	 * @return
	 */
	public int get(int field) {
		return calc.get(field);
	}

	/**
	 * 检查月份是否存在某一个日期
	 * 
	 * @param date
	 * @param month_of_date
	 * @return
	 */
	public static boolean existed(Date date, int month_of_date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return max >= month_of_date;
		
	}
	
	

	// public static String formatString(String dateStr){
	// /*
	// "default": "ddd mmm dd yyyy HH:MM:ss",
	// shortDate: "m/d/yy",
	// mediumDate: "mmm d, yyyy",
	// longDate: "mmmm d, yyyy",
	// fullDate: "dddd, mmmm d, yyyy",
	// shortTime: "h:MM TT",
	// mediumTime: "h:MM:ss TT",
	// longTime: "h:MM:ss TT Z",
	// isoDate: "yyyy-mm-dd",
	// isoTime: "HH:MM:ss",
	// isoDateTime: "yyyy-mm-dd'T'HH:MM:ss",
	// isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
	// */
	//
	// return "";
	// }

}
