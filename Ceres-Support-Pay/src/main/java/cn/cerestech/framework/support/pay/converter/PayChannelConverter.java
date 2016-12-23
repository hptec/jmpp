package cn.cerestech.framework.support.pay.converter;

import javax.persistence.Converter;

import cn.cerestech.framework.support.pay.enums.PayChannel;
import cn.cerestech.framework.support.persistence.coventer.AbstractAttributeConverter;


@Converter(autoApply = true)
public class PayChannelConverter extends AbstractAttributeConverter<PayChannel> {

}
