package cn.cerestech.framework.core.strings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.base.Strings;

public class Unique {
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
	
	public static void main(String[] args) {
		System.out.println(new Date().getTime());
	}

}
