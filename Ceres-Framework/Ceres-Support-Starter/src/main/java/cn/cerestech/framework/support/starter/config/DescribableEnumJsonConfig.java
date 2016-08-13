package cn.cerestech.framework.support.starter.config;

import java.lang.reflect.Type;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.core.json.Jsons;

@Component
public class DescribableEnumJsonConfig implements ApplicationRunner {

	Logger log = LogManager.getLogger();

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Jsons.registerAdapter(DescribableEnum.class, new JsonSerializer<DescribableEnum>() {

			@Override
			public JsonElement serialize(DescribableEnum src, Type typeOfSrc, JsonSerializationContext context) {
				if (src == null) {
					return JsonNull.INSTANCE;
				} else {
					return context.serialize(src.key());
				}
			}
		});

	}
}
