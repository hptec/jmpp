package cn.cerestech.framework.support.persistence.config;

import java.lang.reflect.Type;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.collection.internal.PersistentBag;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import cn.cerestech.framework.core.json.Jsons;

@Component
public class PersistentBagConfig implements ApplicationRunner {

	Logger log = LogManager.getLogger();

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Jsons.registerAdapter(PersistentBag.class, new JsonSerializer<PersistentBag>() {

			@Override
			public JsonElement serialize(PersistentBag src, Type typeOfSrc, JsonSerializationContext context) {
				// 忽略Lazy
				return null;
			}

		});

	}
}
