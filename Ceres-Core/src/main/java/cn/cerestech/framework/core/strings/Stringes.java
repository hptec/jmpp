package cn.cerestech.framework.core.strings;

public class Stringes {
	
	
	/**
	 * 根据参数依次替换text中的{i}变量， i为序列号， 从0 开始， 和replacement 的序列一致
	 * @param text
	 * @param replacement
	 * @return
	 */
	public static String replaceBySequence(String text, String... replacement) {
		for (int i = 0; i < replacement.length; i++) {
			text = text.replaceAll("\\{" + i + "\\}", replacement[i]);
		}
		return text;
	}
}
