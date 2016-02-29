package cn.cerestech.framework.pay.linalian.web;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.pay.linalian.bean.WebPayEntity;
import cn.cerestech.framework.pay.linalian.utils.SignUtils;

public class LLWebPay {
public static final String gate_way = "https://yintong.com.cn/payment/bankgateway.htm";
	
	public static Result payForm(WebPayEntity entity, String payMd5Key, String payRsaKey){
		if(entity != null && entity.validate()){
			String sign = "";
			if("RSA".equals(entity.getSign_type())){
				sign = SignUtils.signRSA(entity, payRsaKey);
			}else{
				sign = SignUtils.signMd5(entity, payMd5Key);
			}
			
			entity.setSign(sign);
			StringBuffer form = new StringBuffer("");
			form.append("<form id=\"LLpay\" action=\"").append(gate_way).append("\" method=\"post\">").append("<br/>");
			Arrays.asList(entity.getClass().getDeclaredFields()).forEach(filed->{
				PropertyDescriptor des;
				try {
					des = new PropertyDescriptor(filed.getName(), entity.getClass());
					Method get = des.getReadMethod();
					Object valobj = get.invoke(entity);
					String val = valobj != null ?valobj.toString():"";
					if(StringUtils.isNotBlank(val)){
						form.append("<input name=\""+filed.getName()+"\" value='"+val+"'>");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			form.append("</form>");
			return Result.success().setObject(form.toString());
		}else{
			return Result.error().setMessage("缺少参数");
		}
	}
}
