package cn.cerestech.middleware.sms.converter;

import javax.persistence.Converter;

import cn.cerestech.framework.support.persistence.coventer.AbstractAttributeConverter;
import cn.cerestech.middleware.sms.enums.SmsState;

@Converter(autoApply = true)
public class SmsStateConverter extends AbstractAttributeConverter<SmsState> {

}
