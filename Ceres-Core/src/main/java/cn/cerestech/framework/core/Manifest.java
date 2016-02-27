package cn.cerestech.framework.core;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.google.common.io.Resources;

import cn.cerestech.framework.core.json.Jsons;

@SuppressWarnings("unchecked")
public class Manifest {

	private static Map<String, Object> DATA = null;

	static {
		URL url = Resources.getResource("manifest.json");
		if (url == null) {
			throw new IllegalArgumentException("manifest.json not exist.");
		}
		String json = "{}";
		try {
			json = Resources.toString(url, Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		DATA = Jsons.fromJson(json, HashMap.class);
	}

	public static String getName() {
		return DATA.getOrDefault("name", "").toString();
	}

	public static String getVersion() {
		return DATA.getOrDefault("version", "").toString();
	}

	public static String getModule(String name) {
		Map<String, Object> modules = (Map<String, Object>) DATA.get("modules");
		if (modules == null) {
			return null;
		}
		Map<String, Object> module = (Map<String, Object>) modules.get(name);
		if (module == null) {
			return "{}";
		} else {
			return Jsons.toJson(module);
		}

	}

	public static void main(String[] argus) {
		System.out.println(Manifest.getModule("console1"));
	}
}
