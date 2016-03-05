package cn.cerestech.framework.pay.wxpay.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.core.Dates;
import cn.cerestech.framework.core.HttpIOutils;
import cn.cerestech.framework.core.XmlUtils;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.pay.bean.PayNotifyBean;
import cn.cerestech.framework.pay.enums.PayChannel;
import cn.cerestech.framework.pay.service.PayServices;
import cn.cerestech.framework.pay.wxpay.bean.PayNotify;
import cn.cerestech.framework.pay.wxpay.bean.PayNotifyBack;
import cn.cerestech.framework.pay.wxpay.publics.NotifyFeedBackUtil;

@Service
public final class WxPayService {
	@Autowired(required=false)
	PayServices payService;
	@Autowired
	MpPayConfigService mpPayConfig;
	
	public void payResult(HttpServletRequest request, HttpServletResponse response){
		String buffer = HttpIOutils.inputHttpContent(request);
		
		if(StringUtils.isNotBlank(buffer)){
			String payKey = mpPayConfig.payKey();
			PayNotify notify = NotifyFeedBackUtil.notifyBack(buffer);
			boolean legal = NotifyFeedBackUtil.isLegal(notify, payKey);
			System.out.println("校验前：payKey:"+payKey+" 校验结果："+legal + " returnCode : "+notify.getReturn_code());
			
			System.out.println("- 支付回调-start---------------------------------------------------------------------------------------");
			System.out.println(Jsons.toJson(notify));
			System.out.println("- 支付回调-end---------------------------------------------------------------------------------------");
			if(legal && "SUCCESS".equals(notify.getReturn_code())){//表示来源合法//且支付成功
				if("SUCCESS".equals(notify.getResult_code())){//支付成功
					String localNo = notify.getOut_trade_no();
					String thirdNo = notify.getTransaction_id();
					BigDecimal totalFee = new BigDecimal(Integer.parseInt(notify.getTotal_fee())/100.0);
					String attach = notify.getAttach();
					PayNotifyBean bean = new PayNotifyBean();
					bean.setAttach(attach);
					bean.setChannel(PayChannel.WXPAY);
					bean.setLocal_trade_no(localNo);
					bean.setMoney(totalFee);
					bean.setPay_time(Dates.parse(notify.getTime_end(),"yyyyMMddHHmmss"));
					bean.setThird_trade_no(thirdNo);
					
					Result ret = payService.payNotify(bean);
					if(ret.isSuccess()){
						
					}else{
						
					}
				}else{//支付失败
				}
				PayNotifyBack back = new PayNotifyBack();
				back.setReturn_code("SUCCESS");
				back.setReturn_msg("OK");
				try {
					HttpIOutils.outputContent(response, XmlUtils.objectToXml(back, "xml").getBytes("UTF-8"), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
