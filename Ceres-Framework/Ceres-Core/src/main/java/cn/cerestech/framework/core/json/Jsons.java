package cn.cerestech.framework.core.json;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class Jsons {

	private Logger log = LogManager.getLogger();

	private JsonElement root;
	private Boolean serializeNull = Boolean.FALSE;
	private Boolean toUnicode = Boolean.TRUE;
	private Boolean prettyPrint = Boolean.FALSE;

	/**
	 * 转换成为json时，表示不会将中文等符号转化成unicode 编码的转义字符
	 * 
	 * @return
	 */
	public Jsons disableUnicode() {
		toUnicode = Boolean.FALSE;
		return this;
	}

	/**
	 * 转化时是否序列化null 或者 empty str
	 * 
	 * @return
	 */
	public Jsons serializeNull() {
		serializeNull = Boolean.TRUE;
		return this;
	}

	/**
	 * 按照方便阅读的格式输出
	 * 
	 * @return
	 */
	public Jsons prettyPrint() {
		prettyPrint = Boolean.TRUE;
		return this;
	}

	public <T> T to(Class<T> clazz) {
		return getGson().fromJson(toJson(), clazz);
	}

	public <T> T to(TypeToken<T> typeToken) {
		return getGson().fromJson(toJson(), typeToken.getType());
	}

	public static Jsons from(Object obj) {
		Jsons me = new Jsons();
		if (obj == null) {
			me.root = null;
			return me;
		}
		if (obj instanceof String) {
			// 传入的字符串
			me.root = me.getGson().fromJson(obj.toString(), JsonElement.class);
			return me;
		}

		if (obj instanceof JsonElement) {
			me.root = (JsonElement) obj;
			return me;
		}

		if (obj instanceof JsonObject) {
			me.root = (JsonObject) obj;
			return me;
		}

		me.root = me.getGson().toJsonTree(obj);

		return me;
	}

	/**
	 * 返回一个空的Jsons对象
	 * 
	 * @return
	 */
	public static Jsons empty() {
		return Jsons.from("{}");
	}

	/**
	 * 获取Gson对象
	 * 
	 * @return
	 */
	private Gson getGson() {

		GsonBuilder builder = new GsonBuilder();
		builder = serializeNull ? builder.serializeNulls() : builder;
		builder = toUnicode ? builder : builder.disableHtmlEscaping();
		builder = prettyPrint ? builder.setPrettyPrinting() : builder;

		// 解析日期格式
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

	public String toJson() {
		return getGson().toJson(root);
	}

	public String toPrettyJson() {
		Jsons me = new Jsons();
		me.root = root;
		return me.prettyPrint().toJson();
	}

	public Jsons printPrettyJson() {
		log.trace(this.toPrettyJson());
		return this;
	}

	public Jsons get(String... nodeName) {
		JsonElement root = this.root;
		if (root == null)
			return new Jsons();
		for (String node : nodeName) {
			if (root.isJsonObject()) {
				if (root.getAsJsonObject().has(node)) {
					// 拥有属性
					root = root.getAsJsonObject().get(node);
				} else {
					// 没有这个属性,返回空属性
					return Jsons.from(null);
				}
			} else if (root.isJsonNull()) {
				// 空则返回空属性
				return Jsons.from(null);
			} else if (root.isJsonPrimitive()) {
				// 基本类型则抛出错误
				throw new IllegalArgumentException("property [" + node + "] is primitive");
			} else if (root.isJsonArray()) {
				// 数组类型则抛出错误
				throw new IllegalArgumentException("property [" + node + "] is array");
			}
		}
		Jsons ret = new Jsons();
		ret.root = root;
		return ret;
	}

	public Jsons get(int i) {
		if (root.isJsonArray()) {
			Jsons ret = new Jsons();
			ret.root = root.getAsJsonArray().get(i);
			return ret;
		} else {
			// 不是数组类型则抛出错误
			throw new IllegalArgumentException("Is not a array");
		}
	}

	public String asString() {
		return asString(null);
	}

	public String asString(String defaultValue) {
		return root == null ? defaultValue : root.getAsString();
	}

	public BigDecimal asBigDecimal() {
		return asBigDecimal(null);
	}

	public BigDecimal asBigDecimal(BigDecimal defaultValue) {
		return root == null ? defaultValue : root.getAsBigDecimal();
	}

	public BigInteger asBigInteger() {
		return asBigInteger(null);
	}

	public BigInteger asBigInteger(BigInteger defaultValue) {
		return root == null ? defaultValue : root.getAsBigInteger();
	}

	public Boolean asBoolean() {
		return asBoolean(null);
	}

	public Boolean asBoolean(Boolean defaultValue) {
		return root == null ? defaultValue : root.getAsBoolean();
	}

	public Byte asByte() {
		return asByte(null);
	}

	public Byte asByte(Byte defaultValue) {
		return root == null ? defaultValue : root.getAsByte();
	}

	public Character asCharacter() {
		return asCharacter(null);
	}

	public Character asCharacter(Character defaultValue) {
		return root == null ? defaultValue : root.getAsCharacter();
	}

	public Double asDouble() {
		return asDouble(null);
	}

	public Double asDouble(Double defaultValue) {
		return root == null ? defaultValue : root.getAsDouble();
	}

	public Float asFloat() {
		return asFloat(null);
	}

	public Float asFloat(Float defaultValue) {
		return root == null ? defaultValue : root.getAsFloat();
	}

	public Integer asInt() {
		return asInt(null);
	}

	public Integer asInt(Integer defaultValue) {
		return root == null ? defaultValue : root.getAsInt();
	}

	public Long asLong() {
		return asLong(null);
	}

	public Long asLong(Long defaultValue) {
		return root == null ? defaultValue : root.getAsLong();
	}

	public Number asNumber() {
		return asNumber(null);
	}

	public Number asNumber(Number defaultValue) {
		return root == null ? defaultValue : root.getAsNumber();
	}

	public List<Jsons> asList() {
		List<Jsons> list = Lists.newArrayList();
		if (root != null && root.isJsonArray()) {
			root.getAsJsonArray().forEach(ele -> {
				Jsons j = new Jsons();
				j.root = ele;
				list.add(j);
			});
		}
		return list;
	}

	public JsonElement getRoot() {
		return root;
	}

	public Boolean isNull() {
		if (root == null) {
			return Boolean.TRUE;
		} else if (root.isJsonNull()) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	public Boolean isArray() {
		if (isNull()) {
			return Boolean.FALSE;
		} else if (root.isJsonArray()) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	public Boolean isObject() {
		if (isNull()) {
			return Boolean.FALSE;
		} else if (root.isJsonObject()) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public String toString() {
		return this.toPrettyJson();
	}

	/**
	 * 添加值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Jsons put(String key, Object value) {
		if (value == null) {
			return this;
		} else if (value instanceof Jsons) {
			root.getAsJsonObject().add(key, ((Jsons) value).getRoot());
		} else if (value instanceof String) {
			root.getAsJsonObject().addProperty(key, (String) value);
		} else {
			put(key, Jsons.from(value));
		}
		return this;
	}
}
