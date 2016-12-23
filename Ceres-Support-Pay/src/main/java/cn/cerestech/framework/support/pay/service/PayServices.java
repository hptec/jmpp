package cn.cerestech.framework.support.pay.service;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.pay.bean.PayNotifyBean;


/**
 * 支付后台通知接口
 */

public interface PayServices {
	/**
	 * 支付回调
	 * @param local_trade_no：商户系统的订单号
	 * @param third_trade_no：第三方支付平台的订单号
	 * @param money: 支付的金额
	 * @param attach： 附加参数，原样返回
	 * @return
	 */
	public <T> Result<T> payNotify(PayNotifyBean bean);
}
