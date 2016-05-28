package cn.cerestech.middleware.sms.converter;

import javax.persistence.Converter;

import cn.cerestech.framework.support.persistence.coventer.AbstractAttributeConverter;
import cn.cerestech.middleware.sms.enums.SmsProvider;

@Converter(autoApply = true)
public class SmsProviderConverter extends AbstractAttributeConverter<SmsProvider> {

}
