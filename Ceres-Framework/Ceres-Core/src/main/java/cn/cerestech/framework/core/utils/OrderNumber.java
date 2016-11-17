package cn.cerestech.framework.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.base.Strings;

public class OrderNumber {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public static final Integer DEFAULT_COUNTER_LEN = 5;
	private static final AtomicLong tmp = new AtomicLong(1);// 临时变量，记录数据

	public static String newDialyNumber(int maxLen) {
		String NO = sdf.format(new Date()) + Strings.padStart(tmp.addAndGet(1) + "", maxLen, '0');
		return NO;
	}

	public static String newDialyNumber() {
		return newDialyNumber(DEFAULT_COUNTER_LEN);
	}

	/**
	 * 生成自一个指定时间开始的用16进制表示的天数
	 * 
	 * @return
	 */
	public static String getPrefix() {
		Long dayDiff = 0L;
		try {
			dayDiff = new Long((new Date().getTime() - sdf.parse("20160601235959999").getTime()) / 1000 / 60 / 60 / 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return Long.toHexString(dayDiff).toUpperCase();
	}
	public static String getPrefix(Date date) {
		Long dayDiff = 0L;
		try {
			dayDiff = new Long((date.getTime() - sdf.parse("20160601232323000").getTime()) / 1000 / 60 / 60 / 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return Long.toHexString(dayDiff).toUpperCase();
	}
}
