package cn.cerestech.framework.core;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Func {

	protected static Logger log = LogManager.getLogger(Func.class);

	public static String replacablePlain(String text, String... replacement) {
		for (int i = 0; i < replacement.length; i++) {
			text = text.replaceAll("\\{" + i + "\\}", replacement[i]);
		}
		return text;
	}

	/**
	 * 为提供系统文件格式的兼容性(单个目录最大文件数不超过5万个)，id要进行分层展示
	 * 
	 * @param id
	 * @return
	 */
	public static String id2Path(Long id) {
		Long levelCounts = 40000l;
		Long levelOne = id / levelCounts;
		Long levelTwo = id % levelCounts;
		return "/" + levelOne + "/" + levelTwo;
	}

	private static final Long SECOND = 1000L;
	private static final Long MINUTE = 60 * SECOND;
	private static final Long HOUR = 60 * MINUTE;
	private static final Long DAY = 24 * HOUR;
	private static final Long THREEDAY = 3 * DAY;
	private static final SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String timeleft(Date date) {
		Long d1 = date.getTime();
		Long now = new Date().getTime();
		Long diff = now - d1;
		if (diff > 0) {
			// xx天以前
			if (diff < MINUTE) {
				return diff / SECOND + 1 + "秒前";
			} else if (diff < HOUR) {
				return diff / MINUTE + 1 + "分钟前";
			} else if (diff < DAY) {
				return diff / HOUR + 1 + "小时前";
			} else if (diff < THREEDAY) {
				return diff / DAY + 1 + "天前";
			} else {
				return simpleFormat.format(date);
			}
		} else {
			return "//TODO 未来时间计算";
		}
	}

	/**
	 * 解析Key-Value，以;分割。:分割k,v
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, String> parseKeyValues(String str) {
		Map<String, String> keyvalue = Maps.newHashMap();
		Splitter.on(";").omitEmptyStrings().split(str).forEach(pair -> {
			List<String> keyvaluepair = Splitter.on(":").splitToList(pair);
			if (keyvaluepair.size() == 2) {
				keyvalue.put(keyvaluepair.get(0), keyvaluepair.get(1));
			}
		});
		return keyvalue;
	}

	/**
	 * 返回左边集合对右边的补集(即在左边存在，但是在右边不存在的)
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static <T> List<T> leftComplement(List<T> list1, List<T> list2) {
		List<T> result = Lists.newArrayList();

		if (list1 == null || list1.isEmpty()) {
			return result;
		}

		if (list2 == null || list2.isEmpty()) {
			return list1;
		}

		for (T t : list1) {
			if (!list2.contains(t)) {
				result.add(t);
			}
		}

		return result;
	}

	/**
	 * 与 leftComplement 相反
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static <T> List<T> rightComplement(List<T> list1, List<T> list2) {
		return leftComplement(list2, list1);
	}

	/**
	 * 房屋的编号，分别指明管理区-楼幢-单元-房号
	 * 
	 * @author harryhe
	 *
	 */
	public static class HouseNo {
		Integer phaseNo, buildingNo, unitNo;
		String houseNumber;

		public Integer getPhaseNo() {
			return phaseNo;
		}

		public void setPhaseNo(Integer phaseNo) {
			this.phaseNo = phaseNo;
		}

		public Integer getBuildingNo() {
			return buildingNo;
		}

		public void setBuildingNo(Integer buildingNo) {
			this.buildingNo = buildingNo;
		}

		public Integer getUnitNo() {
			return unitNo;
		}

		public void setUnitNo(Integer unitNo) {
			this.unitNo = unitNo;
		}

		public String getHouseNumber() {
			return houseNumber;
		}

		public void setHouseNumber(String houseNumber) {
			this.houseNumber = houseNumber;
		}

	}

	/**
	 * 账单周期
	 * 
	 * @author harryhe
	 *
	 */
	public static class BillPhase {
		private Calendar today = Calendar.getInstance();
		private Integer billday = null;
		private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		private static final SimpleDateFormat formatDisplay = new SimpleDateFormat("yyyy年MM月");

		public BillPhase(Integer billday) {
			Integer day = today.get(Calendar.DAY_OF_MONTH);
			if (day > billday) {
				// 如果日期大于等于账单日，则说明账单是本月。否则就是上月账单还未结束
			} else {
				today.add(Calendar.MONTH, -1);
			}
			this.billday = billday;
		}

		public BillPhase(Calendar date, Integer billday) {
			this.today.setTimeInMillis(date.getTimeInMillis());
			this.billday = billday;
		}

		/**
		 * 上一个周期(生成新对象)
		 * 
		 * @return
		 */
		public BillPhase previous() {
			Calendar cale = Calendar.getInstance();
			cale.setTimeInMillis(today.getTimeInMillis());
			cale.add(Calendar.MONTH, -1);
			BillPhase newPhase = new BillPhase(cale, billday);
			return newPhase;
		}

		public Calendar getDate() {
			return today;
		}

		public BillPhase next() {
			Calendar cale = Calendar.getInstance();
			cale.setTimeInMillis(today.getTimeInMillis());
			cale.add(Calendar.MONTH, 1);
			BillPhase newPhase = new BillPhase(cale, billday);
			return newPhase;
		}

		// 定位
		public BillPhase(Integer billday, String billPhase) {
			this(billday);
			try {
				today.setTime(format.parse(billPhase));
			} catch (ParseException e) {
				log.catching(e);
			}
		}

		/**
		 * 得到值
		 * 
		 * @return
		 */
		public String getPhaseString() {
			return format.format(today.getTime());
		}

		public String getDisplayString() {
			return formatDisplay.format(today.getTime());
		}

	}

	public static class Decimal {
		public static Boolean isZero(BigDecimal dec) {
			if (dec == null || BigDecimal.ZERO.compareTo(dec) == 0) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		}
	}

	private static final String[] cnUnit = { "万", "亿", "兆" };
	private static final String[] cnBase = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };

	/**
	 * 阿拉伯数字转换成汉字数字
	 * 
	 * @param num
	 * @return
	 */
	public static String integerToCn(int num) {
		String serial = Integer.toString(num);
		int len = (int) Math.ceil(serial.length() / 4D);
		int size = serial.length();
		String sequence = "";
		for (int i = 0; i < len; i++) {
			int cur = i * 4;
			int g = ((size - cur - 1) >= 0 && (size - cur - 1) < size) ? Integer.parseInt(serial.substring(size - cur - 1, size - cur)) : 0;
			int s = ((size - cur - 2) >= 0 && (size - cur - 2) < size) ? Integer.parseInt(serial.substring(size - cur - 2, size - cur - 1)) : 0;
			int b = ((size - cur - 3) >= 0 && (size - cur - 3) < size) ? Integer.parseInt(serial.substring(size - cur - 3, size - cur - 2)) : 0;
			int q = ((size - cur - 4) >= 0 && (size - cur - 4) < size) ? Integer.parseInt(serial.substring(size - cur - 4, size - cur - 3)) : 0;
			String sub = "";
			sub += q == 0 ? cnBase[0] : (cnBase[q] + "千");
			sub += b == 0 ? cnBase[0] : (cnBase[b] + "百");
			sub += s == 0 ? cnBase[0] : (cnBase[s] + "十");
			sub += g == 0 ? "" : cnBase[g];
			sub = sub.replace("零零", "零");
			sub = sub.equals("零") ? "" : sub;
			while (sub.contains("零零")) {
				sub = sub.replaceAll("零零", "零");
			}
			if (sub.length() > 1 && sub.endsWith("零")) {
				sub = sub.substring(0, sub.indexOf("零") + 1);
			}
			if (i - 1 >= 0) {
				if (sub.length() > 1 && sub.endsWith(cnBase[0])) {
					sub = sub.substring(0, sub.length() - 1);
				}
				sub += cnUnit[i - 1];
			}
			if (i - 1 > 2) {
				throw new RuntimeException("Number is too large.");
			}
			sequence = sub + sequence;
		}
		while (sequence.contains("零零")) {
			sequence = sequence.replaceAll("零零", cnBase[0]);
		}
		if (sequence.length() > 1 && sequence.startsWith(cnBase[0])) {
			sequence = sequence.substring(1, sequence.length());
		}
		if (sequence.length() > 1 && sequence.endsWith(cnBase[0])) {
			sequence = sequence.substring(0, sequence.length() - 1);
		}
		return sequence;
	}

	/**
	 * A 表示1 B 表示2
	 * 
	 * @param num
	 * @return
	 */
	public static String integerToCapitalLetter(int num) {
		if (num > 26 || num <= 0) {
			return "";
		}
		int cnum = 65 + num - 1;
		char c = (char) cnum;

		return String.valueOf(c);
	}

	/**
	 * 将一个数字转换成多少位长度的字符串 1、如果本身长度超出，则保持本身，否则前面加“0”
	 */
	public static String integerToString(int num, int strLen) {
		String sign = num >= 0 ? "" : "-";
		num = num >= 0 ? num : (num * -1);
		String res = Integer.toString(num);

		while (res.length() < strLen) {
			res = "0" + res;
		}
		return (sign + res);
	}

	public static void main(String[] args) {
		System.out.println(integerToString(-112, 10));
	}

}
