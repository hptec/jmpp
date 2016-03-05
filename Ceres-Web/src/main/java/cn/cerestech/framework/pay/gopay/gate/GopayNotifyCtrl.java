package cn.cerestech.framework.pay.gopay.gate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cerestech.framework.pay.gopay.service.GopayService;
import cn.cerestech.framework.web.support.WebSupport;

@Controller
@RequestMapping("gopay")
public class GopayNotifyCtrl extends WebSupport{
	@Autowired
	GopayService gopayService;
	
	public static final String notify_uri = "/gopay/payNotify";
	
	@RequestMapping("payNotify")
	public @ResponseBody void payNotify(){
		gopayService.payResult(getRequest(), getResponse(), false);
	}
	
}
