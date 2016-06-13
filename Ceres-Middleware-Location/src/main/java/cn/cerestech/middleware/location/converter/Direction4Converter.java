package cn.cerestech.middleware.location.converter;

import javax.persistence.Converter;

import cn.cerestech.framework.support.persistence.coventer.AbstractAttributeConverter;
import cn.cerestech.middleware.location.enums.Direction4;

@Converter(autoApply = true)
public class Direction4Converter extends AbstractAttributeConverter<Direction4> {

}
