package cn.cerestech.framework.core;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class JavaNamingConventer {

	private List<String> contents = Lists.newArrayList();;

	/**
	 * 从下划线转换
	 * 
	 * @param content
	 * @return
	 */
	public static JavaNamingConventer fromUnderscore(String content) {
		return seperateByString(content, "_");
	}

	/**
	 * 从连字符转换
	 * 
	 * @param content
	 * @return
	 */
	public static JavaNamingConventer fromHyphen(String content) {
		return seperateByString(content, "-");
	}

	/**
	 * 从驼峰命名法转换
	 * 
	 * @param content
	 * @return
	 */
	public static JavaNamingConventer fromCamelCase(String content) {
		JavaNamingConventer nc = new JavaNamingConventer();
		char[] charArr = Strings.nullToEmpty(content).toCharArray();
		StringBuffer buffer = new StringBuffer();
		for (char c : charArr) {
			if (isUpperChar(c) && buffer.length() > 0) {
				nc.contents.add(buffer.toString());
				buffer = new StringBuffer();
				buffer.append((c + "").toLowerCase());
			} else {
				buffer.append(c);
			}
		}
		if (buffer.length() > 0) {
			nc.contents.add(buffer.toString());
		}
		return nc;
	}

	/**
	 * 智能判断应该属于那种明明方式
	 * 
	 * @param content
	 * @return
	 */
	public static JavaNamingConventer from(String content) {
		String str = Strings.nullToEmpty(content);
		if (str.indexOf('-') != -1) {
			return JavaNamingConventer.fromHyphen(content);
		} else if (str.indexOf('_') != -1) {
			return JavaNamingConventer.fromUnderscore(content);
		} else {
			return JavaNamingConventer.fromCamelCase(content);
		}
	}

	public String toUnderscore() {
		return connectByString("_");
	}

	public String toHyphen() {
		return connectByString("-");
	}

	public String toCamelCase() {
		StringBuffer buffer = new StringBuffer();
		contents.forEach(str -> {
			if (!Strings.isNullOrEmpty(str)) {
				// 有字母

				// 第一个单词的首字母不用大写
				if (buffer.length() == 0) {
					buffer.append(str);
				} else {
					buffer.append(str.substring(0, 1).toUpperCase());
					buffer.append(str.substring(1));
				}
			}
		});

		return buffer.toString();
	}

	private String connectByString(String conn) {
		StringBuffer buffer = new StringBuffer();
		contents.forEach(str -> {
			buffer.append(str + conn);
		});
		buffer.delete(buffer.length() - 1, buffer.length());
		return buffer.toString();
	}

	private static JavaNamingConventer seperateByString(String content, String sep) {
		JavaNamingConventer nc = new JavaNamingConventer();
		Splitter.on(sep).trimResults().omitEmptyStrings().splitToList(Strings.nullToEmpty(content)).forEach(str -> {
			nc.contents.add(str.toLowerCase());
		});
		return nc;
	}

	private static Boolean isUpperChar(char c) {
		String regex = "^[A-Z]+$";
		return match(regex, c + "");
	}

	private static Boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.lookingAt();
	}

}
