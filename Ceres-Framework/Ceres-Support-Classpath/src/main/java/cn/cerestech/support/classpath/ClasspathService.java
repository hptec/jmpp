package cn.cerestech.support.classpath;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.common.io.Resources;

@Service
public class ClasspathService {

	public static final String VISITE_PREFIX = "/api/classpath/query";

	private Logger log = LogManager.getLogger();

	public byte[] findByUri(String id) {
		byte[] bytes = new byte[0];
		// 过滤掉敏感文件
		if (Strings.isNullOrEmpty(id)) {
			return bytes;
		}
		Set<String> exclude = Sets.newHashSet(".java", ".class", ".mf", ".properties", ".xml");
		if (exclude.contains(Files.getFileExtension(Strings.nullToEmpty(id)))) {
			return bytes;
		}

		try {
			URL url = Resources.getResource(id);
			bytes = Resources.toByteArray(url);
		} catch (IOException e) {
			log.catching(e);
		} catch (IllegalArgumentException e) {
		}
		return bytes;
	}

	public String visitUri(String path) {
		return VISITE_PREFIX + "/" + path;
	}

}
