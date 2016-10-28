package cn.cerestech.middleware.balance.converter;

import javax.persistence.Converter;

import cn.cerestech.framework.support.persistence.coventer.AbstractAttributeConverter;
import cn.cerestech.middleware.balance.enums.BufferedTransState;

@Converter(autoApply = true)
public class BuffererdTransStateConverter extends AbstractAttributeConverter<BufferedTransState> {

}
