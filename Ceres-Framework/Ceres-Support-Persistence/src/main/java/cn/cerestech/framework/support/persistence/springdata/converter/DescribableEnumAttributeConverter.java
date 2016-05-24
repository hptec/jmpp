package cn.cerestech.framework.support.persistence.springdata.converter;

import javax.persistence.AttributeConverter;

import cn.cerestech.framework.core.enums.DescribableEnum;

public class DescribableEnumAttributeConverter implements AttributeConverter<DescribableEnum, String> {

	@Override
	public String convertToDatabaseColumn(DescribableEnum de) {
		return de == null ? null : de.key();
	}

	@Override
	public DescribableEnum convertToEntityAttribute(String value) {
		// for (StatusEnum type : StatusEnum.values()) { // 将数字转换为描述
		// if (value.equals(type.getValue())) {
		// return type.getDescription();
		// }
		// }
		return null;
	}
}