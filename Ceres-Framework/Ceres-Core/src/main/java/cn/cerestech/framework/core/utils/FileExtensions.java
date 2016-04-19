package cn.cerestech.framework.core.utils;

import java.util.Set;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

public class FileExtensions {

	/**
	 * 根据扩展名判断是否是图片类型
	 * 
	 * @param ext
	 * @return
	 */
	public static Boolean isImage(String ext) {
		Set<String> imgExts = Sets.newHashSet("jpg", "jpeg", "bmp", "png", "gif", "tiff");
		ext = Strings.nullToEmpty(ext).toLowerCase();
		return imgExts.contains(ext);
	}

	/**
	 * 根据扩展名判断是否是CSS文件
	 * 
	 * @param ext
	 * @return
	 */
	public static Boolean isCss(String ext) {
		return Strings.nullToEmpty(ext).toLowerCase().endsWith("css");
	}

	/**
	 * 根据扩展名判断是否是JS文件
	 * 
	 * @param ext
	 * @return
	 */
	public static Boolean isJs(String ext) {
		return Strings.nullToEmpty(ext).toLowerCase().endsWith("js");
	}

	/**
	 * 根据扩展名判断是否是字体文件
	 * 
	 * @param ext
	 * @return
	 */
	public static Boolean isFont(String ext) {
		Set<String> fontExts = Sets.newHashSet("woff2", "woff", "eot", "ttf");
		ext = Strings.nullToEmpty(ext).toLowerCase();
		return fontExts.contains(ext);
	}

	/**
	 * 根据扩展名判断是否是图片类型
	 * 
	 * @param ext
	 * @return
	 */
	public static Boolean isHtml(String ext) {
		Set<String> imgExts = Sets.newHashSet("htm", "html");
		ext = Strings.nullToEmpty(ext).toLowerCase();
		return imgExts.contains(ext);
	}

	/**
	 * 根据扩展名判断是否是图片类型
	 * 
	 * @param ext
	 * @return
	 */
	public static Boolean isJson(String ext) {
		Set<String> imgExts = Sets.newHashSet("json");
		ext = Strings.nullToEmpty(ext).toLowerCase();
		return imgExts.contains(ext);
	}
}
