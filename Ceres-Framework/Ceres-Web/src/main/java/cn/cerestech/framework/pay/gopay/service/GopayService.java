package cn.cerestech.framework.pay.gopay.service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import cn.cerestech.framework.core.HttpIOutils;
import cn.cerestech.framework.core.service.BaseService;
import cn.cerestech.framework.pay.bean.PayNotifyBean;
import cn.cerestech.framework.pay.enums.PayChannel;
import cn.cerestech.framework.pay.gopay.utils.GopaySignUtil;
import cn.cerestech.framework.pay.gopay.utils.GopaySignUtil.Notify;
import cn.cerestech.framework.pay.service.PayServices;

@Service
public final class GopayService extends BaseService{
	@Autowired(required=false)
	PayServices payService;
	@Autowired
	GopayConfigService gopayConfig;
	
	public void payResult(HttpServletRequest request, HttpServletResponse response, boolean isFront){
		String sign = GopaySignUtil.signNotify(Notify.from(request), gopayConfig.vercode());
		
		System.out.println("system sign : "+ sign);
		System.out.println("request sign : "+request.getParameter("signValue"));
		
		
		Map<String,String> params = Maps.newHashMap();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = valueStr + values[i] + ",";
			}

			params.put(name, valueStr);
		}
		// 国付宝支付返回结果。
		
		System.out.println("- 国付宝后台通知----------------------------------------------");
		if ("0000".equals(request.getParameter("respCode"))) {//支付成功
			String trade_no=request.getParameter("orderId");
			String local_trade_no=request.getParameter("merOrderNum");
			BigDecimal total_fee = new BigDecimal(request.getParameter("tranAmt"));
			
			PayNotifyBean bean = new PayNotifyBean();
			bean.setLocal_trade_no(local_trade_no);
			bean.setChannel(PayChannel.GOPAY);
			bean.setMoney(total_fee);
			bean.setAttach("");
			bean.setThird_trade_no(trade_no);
			
			Notify notify = Notify.from(request);
			boolean legal = GopaySignUtil.checkNotify(notify, gopayConfig.vercode());
			if(legal){//如果校验通过
				payService.payNotify(bean);
				if(!isFront){
					System.out.println("- 国付宝后台通知----------------------------------------------校验成功");
					HttpIOutils.outputContent(response, "success", "UTF-8");
				}
			}else{
				System.out.println("- 国付宝后台通知----------------------------------------------校验失败");
				if(!isFront){
					HttpIOutils.outputContent(response, "fail", "UTF-8");
				}
			}
			
		} else {//支付失败
			System.out.println("- 国付宝后台通知----------------------------------------------交易失败");
			if(!isFront){
				HttpIOutils.outputContent(response, "fail", "UTF-8");
			}
		}
	}
}
