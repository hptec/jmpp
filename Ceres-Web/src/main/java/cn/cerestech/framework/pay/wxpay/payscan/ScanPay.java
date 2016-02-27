package cn.cerestech.framework.pay.wxpay.payscan;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.pay.wxpay.bean.UnifiedOrderEntity;
import cn.cerestech.framework.pay.wxpay.enums.TradeType;
import cn.cerestech.framework.pay.wxpay.publics.UnifiedOrderUtil;

public class ScanPay {
	
	/**
	 * 扫码支付模式一
	 * 开发前，商户必须在公众平台后台设置支付回调URL。URL实现的功能：接收用户扫码后微信支付系统回调的productid和openid
	 * @param entity
	 * @param payKey
	 * @return  暂时未实现
	 */
	@Deprecated
	public static Result payModalOne(UnifiedOrderEntity entity, String payKey){
		entity.setTrade_type(TradeType.NATIVE.key());
		return UnifiedOrderUtil.pay(entity, payKey);
	}
	
	/**
	 * 扫码支付 模式二
	 * @param entity
	 * @param payKey
	 * @return
	 */
	public static Result payModalTwo(UnifiedOrderEntity entity, String payKey){
		entity.setTrade_type(TradeType.NATIVE.key());
		return UnifiedOrderUtil.pay(entity, payKey);
	}
}
