package cn.cerestech.framework.support.starter.config;

import java.lang.reflect.Type;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.json.JsonConverter;
import cn.cerestech.framework.core.json.Jsons;

@Component
public class DescribableEnumJsonConfig implements ApplicationRunner {

	Logger log = LogManager.getLogger();

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Jsons.registerAdapter(DescribableEnum.class, new JsonConverter<DescribableEnum>() {

			@Override
			public JsonElement serialize(DescribableEnum src, Type typeOfSrc, JsonSerializationContext context) {
				if (src == null) {
					return JsonNull.INSTANCE;
				} else {
					JsonElement ele = context.serialize(src.key());
					return ele;
				}
			}

			@Override
			public DescribableEnum deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				if (json == null || json.isJsonNull()) {
					return null;
				} else {
					DescribableEnum de = EnumCollector.forName(typeOfT.getTypeName()).keyOf(json.getAsString());
					return de;
				}
			}
		});

	}
}
