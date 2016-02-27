package cn.cerestech.middleware.weixin.entity;

import com.google.common.base.Strings;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cn.cerestech.framework.core.json.Jsonable;
import cn.cerestech.framework.core.json.Jsons;

public class Status<T> implements Jsonable {
	public static final Integer ABSENT_CODE = -999;
	private int code;
	private String msg;
	private String result;
	private Boolean success = Boolean.TRUE;
	public static final Status ABSENT = new Status(ABSENT_CODE);
	private T object;

	public Status(int code, String msg, String result) {
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	public Status(int code) {
		this.code = code;
	}

	public Status() {
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public T getObject() {
		return object;
	}

	public Status<T> setObject(T object) {
		this.object = object;
		return this;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String asString() {
		return this.result;
	}

	/**
	 * 装载返回结果。
	 * 
	 * @param content
	 * @return
	 */
	public static <T> Status<T> as(String result) {
		Status<T> s = new Status<T>();
		s.setResult(result);

		// 进行成功与否的判断，并加载状态码
		JsonObject obj = s.asJson().getAsJsonObject();
		if (obj.has("errcode")) {
			// 错误消息
			s.setCode(obj.get("errcode").getAsInt());
			s.setMsg(obj.get("errmsg").getAsString());
			s.success = Boolean.FALSE;
			return s;
		}

		return s;
	}

	public Boolean isSuccess() {
		return success;
	}

	public Boolean isAbsent() {
		return ABSENT_CODE.equals(this.getCode());
	}

	public JsonElement asJson() {
		if (Strings.isNullOrEmpty(this.getResult())) {
			return Jsons.from("{}");
		} else {
			JsonElement ret = null;
			ret = Jsons.from(this.getResult());
			return ret;
		}
	}

	public String jsonValue(String key) {
		JsonElement root = asJson();
		if (root != null && root.getAsJsonObject().has(key)) {
			return root.getAsJsonObject().get(key).getAsString();
		}
		return "";
	}

}
