package cn.cerestech.framework.support.persistence.coventer;

import java.util.List;
import java.util.Locale;

import javax.persistence.AttributeConverter;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;

public class LocaleConverter implements AttributeConverter<Locale, String> {

	public String convertToDatabaseColumn(Locale locale) {
		return locale == null ? null : locale.toString();
	}

	public Locale convertToEntityAttribute(String dbData) {
		if (!Strings.isNullOrEmpty(dbData)) {
			List<String> result = Splitter.on("_").splitToList(dbData);
			String language = result.get(0);
			String country = result.get(1);
			return new Locale(language, country);
		}

		return null;
	}

}
