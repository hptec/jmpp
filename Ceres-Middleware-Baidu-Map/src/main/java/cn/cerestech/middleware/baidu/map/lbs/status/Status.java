package cn.cerestech.middleware.baidu.map.lbs.status;

import com.google.common.base.Strings;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cn.cerestech.framework.core.json.Jsons;

/**
 * 百度请求状态工具
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年6月16日
 */
public class Status<T> {
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
		if(Strings.isNullOrEmpty(result)){
			s.success = Boolean.FALSE;
		}

		// 进行成功与否的判断，并加载状态码
		JsonObject obj = s.asJson().getAsJsonObject();
		if (obj.has("status")) {
			int  cd = obj.get("status").getAsInt();
			// 错误消息
			s.setCode(cd);
			s.setMsg(obj.get("message").getAsString());
			s.success = cd == 0 || Boolean.FALSE;
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
			return Jsons.from("{}").getRoot();
		} else {
			JsonElement ret = null;
			ret = Jsons.from(this.getResult()).getRoot();
			return ret;
		}
	}

	public String jsonValue(String key) {
		JsonElement root = asJson();
		if (root != null && root.getAsJsonObject().has(key)) {
			JsonElement ele = root.getAsJsonObject().get(key);
			if(ele.isJsonPrimitive()){
				return ele.getAsString();
			}else{
				return ele.toString();
			}
		}
		return "";
	}

}
