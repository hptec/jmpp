package cn.cerestech.framework.core.json;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class Jsons {
	public static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
	public static Gson nullDisabledGson = new GsonBuilder().setPrettyPrinting().create();

	/**
	 * 获取Gson对象
	 * 
	 * @param nullAble
	 *            ： 转化时是否序列化null 或者 empty str
	 * @param unicode
	 *            : 是否不转化成unicode 默认值为 false 表示会将中文等符号转化成unicode 编码的转义字符
	 * @return
	 */
	public static Gson getGson(boolean nullAble, boolean unicode) {

		GsonBuilder builder = new GsonBuilder();// .setDateFormat("yyyy-MM-dd
												// HH:mm:ss");
		builder = nullAble ? builder.serializeNulls() : builder;
		builder = unicode ? builder : builder.disableHtmlEscaping();
		builder.registerTypeAdapter(java.util.Date.class, new JsonDeserializer<java.util.Date>() {
			@Override
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				try {
					Date d = new GsonBuilder().create().fromJson(json, java.util.Date.class);
					return new Date(d.getTime());
				} catch (Exception e) {
					try {
						java.sql.Date d = new GsonBuilder().create().fromJson(json, java.sql.Date.class);
						return new Date(d.getTime());
					} catch (Exception ee) {
						try {
							return DateFormat.getDateInstance().parse(json.toString());
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				}
				return null;
			}
		});

		return builder.create();
	}

	/**
	 * 特殊类型的Bean转Json
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String json, TypeToken<T> type) {
		if (Strings.isNullOrEmpty(json)) {
			return null;
		}
		try {
			return getGson(false, true).fromJson(json, type.getType());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 普通Bean 转JSon
	 * 
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> cls) {
		return getGson(true, true).fromJson(json, cls);
	}

	/**
	 * 将字符串转换成JSON 对象
	 * 
	 * @param str
	 * @return
	 */
	public static JsonElement from(String json) {
		return fromJson(json, new TypeToken<JsonElement>() {
			@SuppressWarnings("unused")
			private static final long serialVersionUID = 1L;
		});
	}

	/**
	 * Object 转JSON
	 * 
	 * @param obj
	 * @param serializeNull
	 * @param toUnicode
	 *            ： 是否需要转换成html 的格式，即对特殊符号进行转义
	 * @return
	 */
	public static String toJson(Object obj, boolean serializeNull, Boolean toUnicode) {
		return getGson(serializeNull, toUnicode).toJson(obj);
	}

	/**
	 * 
	 * @param obj
	 *            : 对象
	 * @param serializeNull
	 *            ： 是否序列化NULL 属性
	 * @return 值中的特殊符号会被处理成unicode 编码，= & 等
	 */
	public static String toJson(Object obj, boolean serializeNull) {
		return getGson(serializeNull, true).toJson(obj);
	}

	/**
	 * 
	 * @param obj
	 *            : 对象
	 * @param serializeNull
	 *            ： 是否序列化NULL 属性
	 * @return 值中的特殊符号会被处理成unicode 编码，= & 等
	 */
	public static String toJson(Object obj) {
		return getGson(false, true).toJson(obj);
	}

	public static String toPrettyJson(JsonElement obj) {
		return gson.toJson(obj);
	}

	public static String toPrettyJson(String obj) {
		return gson.toJson(Jsons.from(obj));
	}

	/**
	 * 对象转map
	 * 
	 * @param t
	 * @return
	 */
	public static <T> Map<String, Object> toMap(T t) {
		if (t == null) {
			return Maps.newHashMap();
		}
		String tmp = "";
		if (t.getClass().isAssignableFrom(String.class)) {
			tmp = t.toString();
		} else {
			tmp = toJson(t, false);
		}

		if (Strings.isNullOrEmpty(tmp)) {
			return Maps.newHashMap();
		}
		return fromJson(tmp, new TypeToken<Map<String, Object>>() {
			@SuppressWarnings("unused")
			private static final long serialVersionUID = 1L;
		});
	}

}
