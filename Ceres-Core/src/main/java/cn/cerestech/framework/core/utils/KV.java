package cn.cerestech.framework.core.utils;

import java.util.HashMap;
import cn.cerestech.framework.core.json.Jsonable;

@SuppressWarnings("serial")
public class KV extends HashMap<String, Object> implements Jsonable {

	public static KV on() {
		return new KV();
	}

	public KV put(String key, Object v) {
		super.put(key, v);
		return this;
	}

}
