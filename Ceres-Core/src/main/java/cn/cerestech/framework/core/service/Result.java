package cn.cerestech.framework.core.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.core.json.Jsonable;
import cn.cerestech.framework.core.json.Jsons;

public class Result<T> implements Jsonable {

	private Boolean isSuccess = Boolean.TRUE;
	private String type;// 用于区别消息类型
	private String code;
	private T object;
	private String message = "";
	private Map<String, Object> external = Maps.newHashMap();
	private List<String> warnMessages = Lists.newArrayList();

	public static <T> Result<T> success() {
		Result<T> r = new Result<T>();
		r.isSuccess = Boolean.TRUE;
		return r;
	}

	public static <T> Result<T> success(T obj) {
		Result<T> r = new Result<T>();
		r.isSuccess = Boolean.TRUE;
		r.object = obj;
		return r;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> Result<T> setType(String type) {
		this.type = type;
		return (Result<T>) this;
	}

	public String getType() {
		return this.type;
	}

	public static <T> Result<T> error() {
		Result<T> r = new Result<T>();
		r.isSuccess = Boolean.FALSE;
		return r;
	}

	public static <T> Result<T> error(DescribableEnum error) {
		return error(error.key(), error.desc());
	}

	public static <T> Result<T> error(String code, String desc) {
		Result<T> r = new Result<T>();
		r.isSuccess = Boolean.FALSE;

		r.setCode(code);
		r.setMessage(desc);

		return r;
	}

	public static <T> Result<T> error(Result<?> result) {
		Result<T> r = new Result<T>();
		r.isSuccess = Boolean.FALSE;

		r.setCode(result.getCode());
		r.setMessage(result.getMessage());

		return r;
	}

	public void addWarn(String message) {
		warnMessages.add(message);
	}

	public T getObject() {
		return object;
	}

	public String getMessage() {
		return message;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> Result<T> setMessage(String message) {
		this.message = message;
		return (Result<T>) this;
	}

	@SuppressWarnings({ "unchecked" })
	public <E> Result<E> setObject(T object) {
		this.object = object;
		return (Result<E>) this;
	}

	public Boolean isSuccess() {
		return isSuccess;
	}

	public List<String> getWarnMessages() {
		return warnMessages;
	}

	public Result<T> addExternal(String key, Object value) {
		this.external.put(key, value);
		return this;
	}

	public Object getExternal(String key) {
		return this.external.get(key);
	}

	public String toJson(boolean isNullEnable) {
		if (isNullEnable) {
			return Jsons.toJson(this);
		} else {
			return Jsons.nullDisabledGson.toJson(this);
		}
	}

	public String toJson() {
		return toJson(true);
	}

	public String getCode() {
		return code;
	}

	public Result<T> setCode(String code) {
		this.code = code;
		return (Result<T>) this;
	}
}
