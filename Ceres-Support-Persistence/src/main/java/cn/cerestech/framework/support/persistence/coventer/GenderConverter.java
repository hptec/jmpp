package cn.cerestech.framework.support.persistence.coventer;

import javax.persistence.Converter;

import cn.cerestech.framework.core.enums.Gender;
@Converter(autoApply = true)
public class GenderConverter extends AbstractAttributeConverter<Gender> {

}
