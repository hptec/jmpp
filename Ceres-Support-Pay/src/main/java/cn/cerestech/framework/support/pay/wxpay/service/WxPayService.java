package cn.cerestech.framework.support.pay.wxpay.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.core.date.Dates;
import cn.cerestech.framework.core.http.HttpIO;
import cn.cerestech.framework.core.xml.Xmls;
import cn.cerestech.framework.support.pay.bean.PayNotifyBean;
import cn.cerestech.framework.support.pay.enums.PayChannel;
import cn.cerestech.framework.support.pay.service.PayServices;
import cn.cerestech.framework.support.pay.wxpay.bean.PayNotify;
import cn.cerestech.framework.support.pay.wxpay.bean.PayNotifyBack;
import cn.cerestech.framework.support.pay.wxpay.publics.NotifyFeedBackUtil;

import com.google.common.base.Strings;

@Service
public final class WxPayService {
	
	@Autowired(required=false)
	PayServices payService;
	
	@Autowired
	WxPayConfig payConfig;
	
	public void payResult(HttpServletRequest request, HttpServletResponse response){
		String buffer = HttpIO.in(request);
		
		if(!Strings.isNullOrEmpty(buffer)){
			PayNotify notify = NotifyFeedBackUtil.notifyBack(buffer);
			boolean legal = NotifyFeedBackUtil.isLegal(notify, payConfig.getPaykey());
//			System.out.println("校验前：payKey:"+payConfig.getPaykey()+" 校验结果："+legal + " returnCode : "+notify.getReturn_code());
//			
//			System.out.println("- 支付回调-start---------------------------------------------------------------------------------------");
//			System.out.println(Jsons.from(notify).toJson());
//			System.out.println("- 支付回调-end---------------------------------------------------------------------------------------");
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
					bean.setPay_time(Dates.from(notify.getTime_end(),"yyyyMMddHHmmss").toDate());
					bean.setThird_trade_no(thirdNo);
					
					payService.payNotify(bean);
//					Result ret = payService.payNotify(bean);
//					if(ret.isSuccess()){
//						
//					}else{
//						
//					}
				}else{//支付失败
				}
				
				PayNotifyBack back = new PayNotifyBack();
				back.setReturn_code("SUCCESS");
				back.setReturn_msg("OK");
				try {
					HttpIO.out(response, Xmls.objectToXml(back, "xml").getBytes("UTF-8"), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
