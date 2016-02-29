package cn.cerestech.framework.pay.jubao.check;

import java.math.BigDecimal;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;

import cn.cerestech.framework.core.Dates;
import cn.cerestech.framework.core.HttpUtils;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.pay.bean.PayNotifyBean;
import cn.cerestech.framework.pay.enums.PayChannel;
import cn.cerestech.framework.pay.jubao.bean.JubaoPay;
import cn.cerestech.framework.pay.jubao.utils.RSA;

public class PayQuery {
	public static final String url = "http://www.jubaopay.com/apicheck.htm";
	
	public static Result<PayNotifyBean> queryPay(String payid, String partnerid, String cfgFilePath, String certDir){
		
		RSA.intialize(cfgFilePath, certDir);
		JubaoPay pay = new JubaoPay();
		pay.setEncrypt("payid", payid);
		pay.setEncrypt("partnerid", partnerid);
		pay.interpret();//预处理
		
		String message = pay.getMessage();//处理报文
	    String signature = pay.getSignature();//处理签名
	    Map<String, Object> params = Maps.newHashMap();
	    params.put("message", message);
	    params.put("signature", signature);
	    
	    String res = HttpUtils.post(url, params);
	    
	    JsonElement json = Jsons.from(res);
	    if(json != null && json.getAsJsonObject().has("message") && json.getAsJsonObject().has("signature")){
	    	JubaoPay result = new JubaoPay();
	    	result.decrypt(json.getAsJsonObject().get("message").getAsString(), json.getAsJsonObject().get("signature").getAsString());
	    	
	    	String pid  = result.getEncrypt("payid");
	    	String ptid = result.getEncrypt("partnerid");
	    	String ono = result.getEncrypt("orderNo");
	    	String amount = result.getEncrypt("amount");
	    	String state = result.getEncrypt("state");//非 2 的都是支付失败
	    	String tm = result.getEncrypt("modifyTime");//支付时间(yyyyMMddHHmmss)
	    	String nm = result.getEncrypt("payerName");
	    	
	    	if("2".equals(state)){//支付成功
	    		PayNotifyBean bean = new PayNotifyBean();
	    		bean.setLocal_trade_no(pid);
	    		bean.setThird_trade_no(ono);
	    		bean.setMoney(new BigDecimal(amount));
	    		bean.setPay_time(Dates.parse(tm));
	    		bean.setChannel(PayChannel.JUBAOPAY);
	    		return Result.success().setObject(bean);
	    	}
	    }
		return Result.error().setMessage("查询失败");
	}
	
	
	public static void main(String[] args) {
		String payid = "111111002";
		String partnerid = "15081918511819594076";
		String cfgFilePath = "/Users/bird/Documents/电子书/聚宝云计费/e7db/jubaopay.ini";
		String certDir = "/Users/bird/Documents/电子书/聚宝云计费/e7db";
		Result res = queryPay(payid, partnerid, cfgFilePath, certDir);
		System.out.println(Jsons.toJson(res));
	}
}
