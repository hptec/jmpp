package cn.cerestech.framework.support.starter;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.utils.FileExtensions;

public class ContentType {

	/**
	 * 根据扩展名返回content type
	 * 
	 * @param ext
	 * @return
	 */
	public static String getByExtension(String ext) {

		if (FileExtensions.isImage(ext)) {
			return "image/" + ext;//
		} else if (FileExtensions.isCss(ext)) {
			return "text/css";
		} else if (FileExtensions.isJs(ext)) {
			return "application/x-javascript";
		} else if (FileExtensions.isFont(ext)) {
			return "application/font-" + Strings.nullToEmpty(ext).toLowerCase();
		} else if (FileExtensions.isHtml(ext)) {
			return "text/html";
		} else if (FileExtensions.isJson(ext)) {
			return "application/json";
		} else {
			return "";// 下载
		}
	}

}
