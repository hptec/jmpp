package cn.cerestech.framework.pay.linalian.gate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cerestech.framework.pay.linalian.service.LlPayService;
import cn.cerestech.framework.web.support.WebSupport;

@Controller
@RequestMapping("llpay")
public class LlNotifyCtrl extends WebSupport{
	
	public static final String notify_uri = "/llpay/payNotify";
	
	@Autowired
	LlPayService llpayService;
	
	@RequestMapping("payNotify")
	public @ResponseBody void payNotify(){
		llpayService.payResult(getRequest(), getResponse(), false);
	}
}
