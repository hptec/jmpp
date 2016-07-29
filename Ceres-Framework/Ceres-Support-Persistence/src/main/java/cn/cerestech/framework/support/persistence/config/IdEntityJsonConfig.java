package cn.cerestech.framework.support.persistence.config;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import cn.cerestech.framework.core.json.JsonIgnore;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.persistence.IdEntity;

@Component
public class IdEntityJsonConfig implements ApplicationRunner {

	Logger log = LogManager.getLogger();

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Jsons.registerAdapter(IdEntity.class, new JsonSerializer<IdEntity>() {

			@Override
			public JsonElement serialize(IdEntity src, Type typeOfSrc, JsonSerializationContext context) {
				JsonObject ele = new JsonObject();
				if (src != null) {
					Field[] fields = src.getClass().getDeclaredFields();
					for (Field f : fields) {
						if (!f.isAnnotationPresent(JsonIgnore.class)) {
							try {
								f.setAccessible(true);
								Object value = f.get(src);
//								log.trace(value + " - " + f.toString());
								ele.add(f.getName(), context.serialize(value));
							} catch (IllegalArgumentException | IllegalAccessException e) {
								log.error(e);
							}
						} else {
//							log.trace("Json忽略:" + f.toString());
						}
					}
				}
				return ele;
			}
		});

	}
}
