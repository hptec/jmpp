package cn.cerestech.framework.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class Dates {
	public static final String DATE_TIME_MS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE = "yyyy-MM-dd";
	public static final String TIME_MS = "HH:mm:ss.SSS";
	public static final String TIME = "HH:mm:ss";

	public static SimpleDateFormat getFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	/**
	 * 返回时间格式字符串，如果pa 为空 则返回默认格式时间字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param time
	 * @param pa
	 * @return
	 */
	public static String format(Date time, String pa) {
		if (time == null) {
			return "";
		}

		if (StringUtils.isBlank(pa)) {
			pa = DATE_TIME;
		}
		return getFormat(pa).format(time);
	}

	/**
	 * 返回时间字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param time
	 * @return
	 */
	public static String format(Date time) {
		return format(time, null);
	}

	/**
	 * 返回时间字符串 yyyy-MM-dd
	 * 
	 * @param time
	 * @return
	 */
	public static String formatDate(Date time) {
		return format(time, DATE);
	}

	/**
	 * 返回时间字符串 HH:mm:ss
	 * 
	 * @param time
	 * @return
	 */
	public static String formatTime(Date time) {
		return format(time, TIME);
	}

	/**
	 * 返回时间字符串 yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @param time
	 * @return
	 */
	public static String formatDateTimeMs(Date time) {
		return format(time, DATE_TIME_MS);
	}

	/**
	 * 加上天数，或者减去天数
	 * 
	 * @param time
	 * @param num
	 * @return
	 */
	public static Date addDay(Date time, int num) {
		return add(time, Calendar.DATE, num);
	}

	public static Date addHour(Date time, int num) {
		return add(time, Calendar.HOUR, num);
	}

	public static Date addMinute(Date time, int num) {
		return add(time, Calendar.MINUTE, num);
	}

	public static Date addMillisecond(Date time, int num) {
		return add(time, Calendar.MILLISECOND, num);
	}

	public static Date addMonth(Date time, int num) {
		return add(time, Calendar.MONTH, num);
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
	public static Date add(Date time, int field, int num) {
		if (time == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(field, num);
		return c.getTime();
	}

	public static Date now() {
		return new Date();
	}

	/**
	 * 日期的0点
	 * 
	 * @param date
	 * @return
	 */
	public static Date dayStart(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}

	/**
	 * 时间的 23：59：59：999
	 * 
	 * @param date
	 * @return
	 */
	public static Date dayEnd(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);

		return c.getTime();
	}

	/**
	 * 解析时间字符串
	 * 
	 * @param dateStr
	 * @param format：
	 *            时间字符串格式： 如果为null 或者 blank 则使用 yyyy-MM-dd HH:mm:ss 格式进行解析
	 * @return
	 */
	public static Date parse(String dateStr, String format) {
		if (StringUtils.isBlank(format)) {
			format = DATE_TIME;
		}
		try {
			return getFormat(format).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析时间字符串为date yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parse(String dateStr) {
		return parse(dateStr, null);
	}

	/**
	 * 解析时间字符串为date yyyy-MM-dd
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseDate(String dateStr) {
		return parse(dateStr, DATE);
	}

	/**
	 * 解析时间字符串为 HH:mm:ss
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseTime(String dateStr) {
		return parse(dateStr, TIME);
	}

	/**
	 * 解析时间字符串为 yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseDateTimeMillisecond(String dateStr) {
		return parse(dateStr, DATE_TIME_MS);
	}

	/**
	 * 计算两个时间相差的毫秒数
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public static Long distance(Date from, Date to) {
		long f = from != null ? from.getTime() : 0;
		long t = to != null ? to.getTime() : 0;
		return f - t;
	}

	/**
	 * 判断 from 是否在 给定 to 时间 的 field 的num 距离之后
	 * 
	 * @param from
	 * @param to
	 * @param num
	 * @param field
	 * @return
	 */
	public static boolean after(Date from, Date to, int num, int field) {
		Date toNow = add(to, field, num);
		return from.after(toNow);
	}

	/**
	 * 返回 from 是否 大于 to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean after(Date from, Date to) {
		return from.after(to);
	}

	/**
	 * 相差天数
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public static float distanceDay(Date from, Date to) {
		return (distance(from, to) * 1.0f) / (3600 * 24 * 1000);
	}

	/**
	 * 两个日期是否是同一天
	 * 
	 * @return
	 */
	public static boolean sameDay(Date from, Date to) {
		if (from == null || to == null) {
			return false;
		}
		return formatDate(from).equals(formatDate(to));
	}

	/**
	 * 
	 * @param date
	 * @param field:
	 *            Calendar. field
	 * @return
	 */
	public static int get(Date date, int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(field);
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
	
	
//	public static String formatString(String dateStr){
//		/*
//		"default":      "ddd mmm dd yyyy HH:MM:ss",
//		shortDate:      "m/d/yy",
//		mediumDate:     "mmm d, yyyy",
//		longDate:       "mmmm d, yyyy",
//		fullDate:       "dddd, mmmm d, yyyy",
//		shortTime:      "h:MM TT",
//		mediumTime:     "h:MM:ss TT",
//		longTime:       "h:MM:ss TT Z",
//		isoDate:        "yyyy-mm-dd",
//		isoTime:        "HH:MM:ss",
//		isoDateTime:    "yyyy-mm-dd'T'HH:MM:ss",
//		isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
//		*/
//		
//		return "";
//	}
	
	
	
	
	
	
	
	

}
