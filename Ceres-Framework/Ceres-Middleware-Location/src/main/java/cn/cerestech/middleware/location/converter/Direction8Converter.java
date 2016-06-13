package cn.cerestech.middleware.location.converter;

import javax.persistence.Converter;

import cn.cerestech.framework.support.persistence.coventer.AbstractAttributeConverter;
import cn.cerestech.middleware.location.enums.Direction8;

@Converter(autoApply = true)
public class Direction8Converter extends AbstractAttributeConverter<Direction8> {

}
