package cn.cerestech.framework.pay.jubao.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.pay.jubao.bean.JubaoPay;
import cn.cerestech.framework.pay.jubao.bean.PayEntity;
import cn.cerestech.framework.pay.jubao.enums.Channel;
import cn.cerestech.framework.pay.jubao.enums.DefaultTab;
import cn.cerestech.framework.pay.jubao.wap.WapPay;
import cn.cerestech.framework.pay.jubao.web.WebPay;


public class UnifiedPay {
	
	public static Result pay(PayEntity entity, String cfgFilePath, String certDir, Channel channel, boolean formOrUrl, DefaultTab tab){
		tab = tab == null?DefaultTab.ALIPAY:tab;
		if(entity == null || !entity.validate() || StringUtils.isBlank(cfgFilePath) || channel == null){
			return Result.error().setMessage("参数错误");
		}
		
		String postUrl = "";
		switch (channel) {
			case WAP:
				postUrl = WapPay.url;
				break;
			case WEB:
				postUrl = WebPay.url;
				break;
			default:
				return Result.error().setMessage("请填写支付通道");
		}
		
		
		if(entity!= null && entity.validate()){
			RSA.intialize(cfgFilePath, certDir);
			JubaoPay pay = new JubaoPay();
			Field[] fields = PayEntity.class.getDeclaredFields();
			for (Field filed : fields) {
				PropertyDescriptor des;
				try {
					des = new PropertyDescriptor(filed.getName(), PayEntity.class);
					Method get = des.getReadMethod();
					Object valobj = get.invoke(entity);
					String val = valobj != null ?valobj.toString():"";
					if(StringUtils.isNotBlank(val)){
						pay.setEncrypt(filed.getName(), val);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			pay.interpret();//预处理
			String message = pay.getMessage();//处理报文
		    String signature = pay.getSignature();//处理签名
		    StringBuffer form = new StringBuffer();
		    if(formOrUrl){
		    	form.append("<form id=\"jbPayBillForm\" action=\"").append(postUrl).append("\" method=\"post\">").append("<br/>");
		    	form.append("<input type=\"hidden\" name=\"tab\" value=\"").append(tab.key()).append("\"/>").append("<br/>");
		    	form.append("<input type=\"hidden\" name=\"message\" value=\"").append(message).append("\"/>").append("<br/>");
		    	form.append("<input type=\"hidden\" name=\"signature\" value=\"").append(signature).append("\"/>").append("<br/>");
		    	form.append("<input type=\"hidden\" name=\"payMethod\" value=\"").append(entity.getPayMethod()).append("\"/>");
		    	form.append("</form>");
		    }else{
		    	form.append(postUrl).append("?message=").append(message).append("&signature=").append(signature).append("&payMethod=").append(entity.getPayMethod());
		    }
		    return Result.success().setObject(form.toString());
		}else{
			return Result.error().setMessage("参数错误");
		}
	}
}
