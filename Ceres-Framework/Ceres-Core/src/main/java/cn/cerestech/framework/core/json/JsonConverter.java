package cn.cerestech.framework.core.json;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public interface JsonConverter<T> extends JsonSerializer<T>, JsonDeserializer<T> {

}
