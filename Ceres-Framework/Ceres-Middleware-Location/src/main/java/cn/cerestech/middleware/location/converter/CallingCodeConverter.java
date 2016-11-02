package cn.cerestech.middleware.location.converter;

import javax.persistence.Converter;

import cn.cerestech.framework.support.persistence.coventer.AbstractAttributeConverter;
import cn.cerestech.middleware.location.mobile.CallingCode;

@Converter(autoApply = true)
public class CallingCodeConverter extends AbstractAttributeConverter<CallingCode> {

}
