package cn.cerestech.framework.core;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.io.Resources;

public class ResourceLoader {
	private static Logger log = LogManager.getLogger(ResourceLoader.class);

	public static String[] readLines(String fileInClasspath) {
		List<String> lines = Lists.newArrayList();
		try {
			// 读取文件内容
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(Resources.getResource(
							fileInClasspath).openStream()),Charset.forName("UTF-8")));
			// Resources.getResourceAsReader(fileInClasspath));
			// 声明存储空间并存储内容
			String str = null;
			while ((str = reader.readLine()) != null) {
				lines.add(str);
			}

		} catch (IOException e) {
			log.throwing(e);
		}
		return lines.toArray(new String[lines.size()]);
	}

	public static String readContent(String fileInClasspath) {
		StringBuffer result = new StringBuffer();
		try {
			// 读取文件内容
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(Resources.getResource(
							fileInClasspath).openStream()),Charset.forName("UTF-8")));
			// 转存内容
			String str = null;
			while ((str = reader.readLine()) != null) {
				result.append(str + "\n");
			}

		} catch (IOException e) {
			log.throwing(e);
		}
		return result.toString();
	}
}
