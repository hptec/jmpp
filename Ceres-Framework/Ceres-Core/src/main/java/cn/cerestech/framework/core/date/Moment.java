package cn.cerestech.framework.core.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Strings;

public class Moment {
	public static final String DATE_TIME_MS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE = "yyyy-MM-dd";
	public static final String TIME_MS = "HH:mm:ss.SSS";
	public static final String TIME = "HH:mm:ss";

	private Calendar calc = Calendar.getInstance();

	private Boolean roundUp = Boolean.FALSE;

	public Moment roundUp() {
		roundUp = Boolean.TRUE;
		return this;
	}

	public Moment clone() {
		return from(calc.getTimeInMillis());
	}

	public static Moment now() {
		return from(new Date());
	}

	public static Moment beginngOfToday() {
		return from(now().beginningOfDay().toTimeInMillis());
	}

	public static Moment endOfToday() {
		return from(now().endOfDay().toTimeInMillis());
	}

	public static Moment from(Date date) {
		Moment dts = new Moment();
		dts.calc.setTime(date);
		return dts;
	}

	public static Moment from(Long timeInMillis) {
		Moment dts = new Moment();
		dts.calc.setTimeInMillis(timeInMillis);
		return dts;
	}

	public SimpleDateFormat getFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	public String format(String pattern) {
		return getFormat(pattern).format(calc.getTime());
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
	public Moment addDate(int num) {
		return add(Calendar.DAY_OF_MONTH, num);
	}

	public Moment addYear(int num) {
		return add(Calendar.YEAR, num);
	}

	public Moment addHour(int num) {
		return add(Calendar.HOUR, num);
	}

	public Moment addMinute(int num) {
		return add(Calendar.MINUTE, num);
	}

	public Moment addSecond(int num) {
		return add(Calendar.SECOND, num);
	}

	public Moment addMillisecond(int num) {
		return add(Calendar.MILLISECOND, num);
	}

	public Moment addMonth(int num) {
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
	public Moment add(int field, int num) {
		Moment dts = clone();
		dts.calc.add(field, num);
		return dts;
	}

	/**
	 * 日期的00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public Moment beginningOfDay() {
		Moment dts = clone();
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
	public Moment endOfDay() {
		Moment dts = clone();
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
	public static Moment from(String dateStr, String format) {
		SimpleDateFormat sdf = Strings.isNullOrEmpty(format) ? new SimpleDateFormat(DATE_TIME)
				: new SimpleDateFormat(format);
		try {
			Date dt = sdf.parse(dateStr);
			return Moment.from(dt);
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
	public static Moment fromDateTime(String dateStr) {
		return from(dateStr, DATE_TIME);
	}

	/**
	 * 解析时间字符串为date yyyy-MM-dd
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Moment fromDate(String dateStr) {
		return from(dateStr, DATE);
	}

	/**
	 * 解析时间字符串为 HH:mm:ss
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Moment fromTime(String dateStr) {
		return from(dateStr, TIME);
	}

	/**
	 * 解析时间字符串为 yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Moment fromDateTimeMs(String dateStr) {
		return from(dateStr, DATE_TIME_MS);
	}

	/**
	 * 比较两个时间按照时间单位的差值，默认情况下有余数就截断，除非调用roundUp()，告诉程序有余数要增加一个单位周期。<br/>
	 * 比如: 1.5天在默认情况下返回1天，在roundUp()指令后返回2天
	 * 
	 * @param to
	 * @param unit
	 * @return
	 */
	public Long diff(Calendar to, TimeUnit unit) {
		if (to == null) {
			throw new IllegalArgumentException("from or to cannot be null");
		}
		Long diff = to.getTimeInMillis() / unit.toMillis(1);
		if (roundUp && to.getTimeInMillis() % unit.toMillis(1) > 0) {
			diff++;
		}
		return diff;
	}

	public Long diff(Date to, TimeUnit unit) {
		Calendar calc = Calendar.getInstance();
		calc.setTime(to);
		return diff(calc, unit);
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
