package cn.cerestech.framework.support.pay.wxpay.gate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cerestech.framework.support.pay.wxpay.service.WxPayService;
import cn.cerestech.framework.support.starter.web.WebSupport;



@Controller
@RequestMapping("wxpaynotify")
public class WxNotifyCtrl extends WebSupport{
	@Autowired
	WxPayService wxPayService;
	
	public static final String notify_uri = "/wxpaynotify/payNotify"; 
	
	@RequestMapping("payNotify")
	public @ResponseBody void payNotify(){
		wxPayService.payResult(getRequest(), getResponse());
	}
}
