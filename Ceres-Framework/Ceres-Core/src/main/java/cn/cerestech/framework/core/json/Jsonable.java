package cn.cerestech.framework.core.json;

public interface Jsonable {

	public default String toJson() {
		return Jsons.toJson(this);
	}

	public default String toJson(boolean serializeNull) {
		return Jsons.toJson(this, serializeNull);
	}

	public default String toJson(boolean serializeNull, Boolean toUnicode) {
		return Jsons.toJson(this, serializeNull, toUnicode);
	}

	public default String toPretty() {
		return Jsons.toPrettyJson(Jsons.toJson(this));
	}

	public default String toPretty(String json) {
		return Jsons.toPrettyJson(Jsons.from(json));
	}
}
