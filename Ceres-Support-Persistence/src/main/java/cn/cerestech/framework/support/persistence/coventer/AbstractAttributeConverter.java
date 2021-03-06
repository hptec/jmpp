package cn.cerestech.framework.support.persistence.coventer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.AttributeConverter;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.core.enums.EnumCollector;

public abstract class AbstractAttributeConverter<T extends DescribableEnum> implements AttributeConverter<T, String> {

	public String convertToDatabaseColumn(T attribute) {
		return attribute == null ? null : attribute.key();
	}

	public T convertToEntityAttribute(String dbData) {

		return Strings.isNullOrEmpty(dbData) ? null : EnumCollector.forClass(getType()).keyOf(dbData);
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getType() {
		Class<T> entityClass = null;
		Type t = getClass().getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			entityClass = (Class<T>) p[0];
		}
		return entityClass;
	}
}
