package cn.cerestech.framework.pay.linalian.service;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;

import cn.cerestech.framework.core.Dates;
import cn.cerestech.framework.core.HttpIOutils;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.pay.bean.PayNotifyBean;
import cn.cerestech.framework.pay.enums.PayChannel;
import cn.cerestech.framework.pay.linalian.bean.NotifyBean;
import cn.cerestech.framework.pay.linalian.bean.RetBean;
import cn.cerestech.framework.pay.linalian.utils.SignUtils;
import cn.cerestech.framework.pay.service.PayServices;

@Service
public final class LlPayService {
	
	@Autowired
	LlPayConfigService llpayConfig;
	@Autowired(required=false)
	PayServices payService;
	
	public void payResult(HttpServletRequest request, HttpServletResponse response, boolean isFront){
		RetBean retBean = new RetBean();
        String reqStr = HttpIOutils.inputHttpContent(request);
        if (StringUtils.isBlank(reqStr)){
            retBean.setRet_code("9999");
            retBean.setRet_msg("交易失败");
            if(!isFront){
            	HttpIOutils.outputContent(response, Jsons.toJson(retBean), "UTF-8");
            }
            return;
        }
        System.out.println("接收支付异步通知数据：【" + reqStr + "】");
        Map<String, String> params = Jsons.fromJson(reqStr, new TypeToken<Map<String, String>>() {});
        try{	
            if (!SignUtils.checkSign(params, llpayConfig.md5PayKey(), llpayConfig.govRsaPublicKey())){
                retBean.setRet_code("9999");
                retBean.setRet_msg("交易失败");
                if(!isFront){
                	HttpIOutils.outputContent(response, Jsons.toJson(retBean), "UTF-8");
                }
                System.out.println("支付异步通知验签失败");
                return;
            }
        } catch (Exception e){
            System.out.println("异步通知报文解析异常：" + e);
            retBean.setRet_code("9999");
            retBean.setRet_msg("交易失败");
            if(!isFront){
            	HttpIOutils.outputContent(response, Jsons.toJson(retBean), "UTF-8");
            }
            return;
        }
        NotifyBean notify = Jsons.fromJson(reqStr, new TypeToken<NotifyBean>() {});
        if(notify != null && "SUCCESS".equals(params.get("result_pay"))){
        	PayNotifyBean bean = new PayNotifyBean(notify.getNo_order(), notify.getOid_paybill(), new BigDecimal(notify.getMoney_order()),
        			Dates.parse(notify.getDt_order(), "yyyyMMddHHmmss"), notify.getInfo_order(), PayChannel.LLPAY);
        	payService.payNotify(bean);
        	retBean.setRet_code("0000");
        	retBean.setRet_msg("交易成功");
        	if(!isFront){
        		HttpIOutils.outputContent(response, Jsons.toJson(retBean), "UTF-8");
        	}
        	System.out.println("支付异步通知数据接收处理成功");
        	return;
        }else{
             retBean.setRet_code("9999");
             retBean.setRet_msg("交易失败");
             if(!isFront){
             	HttpIOutils.outputContent(response, Jsons.toJson(retBean), "UTF-8");
             }
             return;
        }
	}
	
	
	public static void main(String[] args) {
		String llgov = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";
		String md5 = "Ceres2015LLpay1014CTD";
		
		String  pvi = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANRpDW7JQ1xwgDxjsnEmxlNqDSK16tDy/v5ARr5Y+DuDUAN+dHr5LzLlS9KasBo2hu1ALSa+gUZ0OHOXdsik40h0B7v3ktUSvVBOHFQy6wN57+nAwz5tftxzeYnaCzZgDpxjK8Shn2c0SvcWM1gMKhhvY5fuVMAjU2pPAb3rA5hjAgMBAAECgYAhnPo+NOVPBJKWe+RqGYu6+YZYntco97s4eu13A9dMe6w20VUXfMVmVXjZPBdwHn7dnpFGl1EX2B5y1F48xDMfIweW4YHjCxvStdjmLuNUhncn5VOWZM9LauSKxAvZnxgaaXopJCPeylX4OkapgYoap8TLtNmgRrT5DnH50ZKpCQJBAP00HLT4dFmAUE1bupNmtKvPgCpcMVLD5VpzKQKmnCVIwXvC464xfMh9fDVMlIjKKHOmx8i6NRznBpghKmcrf+cCQQDWwZrW3LckpIFhjH2vktYtr+STuYcuyBd0Qj71yeookA5B6Z3HGKIqUkRk9vZCSsc7m/JX4JGuffhzfRFRIYQlAkBrtBAF9q1fKNJ/pWYerxBpCNGmsyKT5xoXOGcYZpCC14jdwQ+iGBDRI3eDIHkKGpvMXgQbYQGYsri+W1UzH3C/AkB2e1fu5NSR/cR3yifpfsx1Zk5ohfokADaYaJgNyLlMabXD/ZyTpG6LhNnBDlNs3Y6vv2jjvL0DFPLG3KB6L1CVAkEA6qn7bV04ffyvDMWGJrl0ra1lSVEAHfFlYPtpa+X51Z7tbwgAr0tCZ/0U5lczy7jtNXkEetUAUFPTxEiGrht3hg==";
		String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDUaQ1uyUNccIA8Y7JxJsZTag0iterQ8v7+QEa+WPg7g1ADfnR6+S8y5UvSmrAaNobtQC0mvoFGdDhzl3bIpONIdAe795LVEr1QThxUMusDee/pwMM+bX7cc3mJ2gs2YA6cYyvEoZ9nNEr3FjNYDCoYb2OX7lTAI1NqTwG96wOYYwIDAQAB";
		String res = "{\"dt_order\":\"20151019113144\",\"money_order\":\"0.01\",\"no_order\":\"2015101911313905300004\",\"oid_partner\":\"201507021000394502\",\"oid_paybill\":\"2015101961256020\",\"pay_type\":\"1\",\"result_pay\":\"SUCCESS\",\"settle_date\":\"20151019\",\"sign\":\"S/Y48DDrGmTeVwiAbGQ3C6ZmyEeqcXj3R4jq1rm3Tqs447lB+Hk4EehFWit8Jjp+jRvV6tvtJ/XlprMFDfDhCYeLiQZNRStiGd6fmaeoE4PBK3dnABqcxgLGeaCr/ETzUPME4D125cLDfuOnzDASs7RAuUmR1Yc72jqOYagkqq0=\",\"sign_type\":\"RSA\"}";
		 Map<String, String> params = Jsons.fromJson(res, new TypeToken<Map<String, String>>() {});
		 boolean bol = SignUtils.checkSign(params, md5, llgov);
		System.out.println(bol); 
	}
	
	
}
