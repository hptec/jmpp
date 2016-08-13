package cn.cerestech.framework.support.starter.coverter;

import javax.persistence.Converter;

import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.support.persistence.coventer.AbstractAttributeConverter;

@Converter(autoApply = true)
public class PlatformCategoryConverter extends AbstractAttributeConverter<PlatformCategory> {

}
