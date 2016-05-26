package cn.cerestech.framework.support.persistence.coventer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.AttributeConverter;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.core.enums.EnumCollector;

public abstract class AbstractAttributeConverter<T extends DescribableEnum> implements AttributeConverter<T, String> {

	public String convertToDatabaseColumn(T attribute) {
		return attribute == null ? null : attribute.key();
	}

	public T convertToEntityAttribute(String dbData) {
		return EnumCollector.forClass(getType()).keyOf(dbData);
	}

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
