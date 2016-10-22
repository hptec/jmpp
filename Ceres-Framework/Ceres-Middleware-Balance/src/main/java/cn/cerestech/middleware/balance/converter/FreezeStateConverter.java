package cn.cerestech.middleware.balance.converter;

import javax.persistence.Converter;

import cn.cerestech.framework.support.persistence.coventer.AbstractAttributeConverter;
import cn.cerestech.middleware.balance.enums.FreezeState;

@Converter(autoApply = true)
public class FreezeStateConverter extends AbstractAttributeConverter<FreezeState> {

}
