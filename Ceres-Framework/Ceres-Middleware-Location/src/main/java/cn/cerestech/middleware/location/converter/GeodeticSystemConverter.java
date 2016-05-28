package cn.cerestech.middleware.location.converter;

import javax.persistence.Converter;

import cn.cerestech.framework.support.persistence.coventer.AbstractAttributeConverter;
import cn.cerestech.middleware.location.enums.GeodeticSystem;

@Converter(autoApply = true)
public class GeodeticSystemConverter extends AbstractAttributeConverter<GeodeticSystem> {

}
