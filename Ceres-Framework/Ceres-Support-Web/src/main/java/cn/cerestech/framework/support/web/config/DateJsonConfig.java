package cn.cerestech.framework.support.web.config;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.google.common.primitives.Longs;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import cn.cerestech.framework.core.date.Dates;
import cn.cerestech.framework.core.json.Jsons;

@Configuration
public class DateJsonConfig implements ApplicationRunner {

	Logger log = LogManager.getLogger();

	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		// 应用于JSON的Date类型转换
		Jsons.registerAdapter(java.sql.Date.class, new JsonSerializer<java.sql.Date>() {

			@Override
			public JsonElement serialize(java.sql.Date src, Type typeOfSrc, JsonSerializationContext context) {
				if (src == null) {
					return JsonNull.INSTANCE;
				} else {
					Date to = new Date();
					to.setTime(src.getTime());
					return context.serialize(to);
				}
			}

		});
		Jsons.registerAdapter(Date.class, new JsonDeserializer<Date>() {

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
							try {
								return new Date(json.getAsLong());
							} catch (Exception e2) {
								try {
									return new SimpleDateFormat(Dates.DATE_TIME).parse(json.getAsString());
								} catch (ParseException e3) {
									try {
										Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
												.parse(json.getAsString());
										return date;
									} catch (ParseException e4) {
										e4.printStackTrace();
									}
								}
							}
						}
					}
				}
				return null;
			}
		});

		// 应用于Spring数据绑定的类型转换
		ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter
				.getWebBindingInitializer();
		if (initializer.getConversionService() != null) {
			GenericConversionService genericConversionService = (GenericConversionService) initializer
					.getConversionService();
			genericConversionService.addConverter(new Converter<String, Date>() {

				@Override
				public Date convert(String source) {
					return convertStringToDate(source);
				}

			});
		}
	}

	private Date convertStringToDate(String str) {
		if (Longs.tryParse(str) != null) {
			return new Date(Longs.tryParse(str));
		}

		try {
			return Dates.fromDateTime(str).toDate();
		} catch (Exception e3) {
			try {
				Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(str);
				return date;
			} catch (ParseException e4) {
				return new Date(str);
			}
		}
	}
}
