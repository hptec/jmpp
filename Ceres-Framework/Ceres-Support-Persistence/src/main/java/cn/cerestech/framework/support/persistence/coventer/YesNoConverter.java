package cn.cerestech.framework.support.persistence.coventer;

import javax.persistence.Converter;

import cn.cerestech.framework.core.enums.YesNo;

@Converter(autoApply = true)
public class YesNoConverter extends AbstractAttributeConverter<YesNo> {

}
