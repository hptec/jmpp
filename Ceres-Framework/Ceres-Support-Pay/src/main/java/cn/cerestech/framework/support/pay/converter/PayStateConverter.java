package cn.cerestech.framework.support.pay.converter;

import javax.persistence.Converter;

import cn.cerestech.framework.support.pay.enums.PayState;
import cn.cerestech.framework.support.persistence.coventer.AbstractAttributeConverter;



@Converter(autoApply = true)
public class PayStateConverter extends AbstractAttributeConverter<PayState> {

}
