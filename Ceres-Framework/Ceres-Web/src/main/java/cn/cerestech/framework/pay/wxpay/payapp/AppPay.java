package cn.cerestech.framework.pay.wxpay.payapp;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.pay.wxpay.bean.UnifiedOrderEntity;
import cn.cerestech.framework.pay.wxpay.enums.TradeType;
import cn.cerestech.framework.pay.wxpay.publics.UnifiedOrderUtil;

public class AppPay {
	
	public static Result pay(UnifiedOrderEntity entity, String payKey){
		entity.setTrade_type(TradeType.APP.key());
		return UnifiedOrderUtil.pay(entity, payKey);
	}
}
