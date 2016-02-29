package cn.cerestech.framework.pay.jubao.gate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cerestech.framework.pay.jubao.service.JubaoService;
import cn.cerestech.framework.web.support.WebSupport;

@Controller
@RequestMapping("jbpay")
public class JBNotifyCtrl extends WebSupport{
	
	public static final String notify_uri = "jbpay/payNotify";
	
	@Autowired(required=false)
	JubaoService jbService;
	
	@RequestMapping("payNotify")
	public @ResponseBody void payNotify(){
		jbService.payResult(getRequest(), getResponse(),false);
	}
}
