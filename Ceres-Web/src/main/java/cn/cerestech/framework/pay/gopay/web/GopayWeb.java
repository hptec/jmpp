package cn.cerestech.framework.pay.gopay.web;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.pay.gopay.bean.PayEntity;
import cn.cerestech.framework.pay.gopay.utils.GopaySignUtil;

public class GopayWeb {
	public static final String gateway = "https://gateway.gopay.com.cn/Trans/WebClientAction.do";
//	public static final String gateway_test = "https://gatewaymer.gopay.com.cn/Trans/WebClientAction.do";
	
	public static Result payForm(PayEntity entity, String verCode){
		if(entity.validate()){
			String sign = GopaySignUtil.signPay(entity, verCode);
			entity.setSignValue(sign);
			StringBuffer form = new StringBuffer("");
			form.append("<form action=\""+gateway+"\" method=\"post\" style=\"display:none;\">").append("\n");
			
			Arrays.asList(PayEntity.class.getDeclaredFields()).forEach(filed->{
				PropertyDescriptor des;
				try {
					des = new PropertyDescriptor(filed.getName(), PayEntity.class);
					Method get = des.getReadMethod();
					Object valobj = get.invoke(entity);
					String val = valobj != null ?valobj.toString():"";
					if(StringUtils.isNotBlank(val)){
						form.append("	<input type=\"text\" name=\""+filed.getName()+"\" value=\""+val+"\"/>").append("\n");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			form.append("</form>").append("\n");
			return Result.success().setObject(form);
		}else{
			return Result.error().setMessage("缺少必要参数");
		}
	}
	
	public static void main(String[] args) {
		PayEntity entity = new PayEntity();
		entity.setBackgroundMerUrl("http://www.baidu.com");
		entity.setBankCode("8888");
		entity.setFrontMerUrl("http://www.baidu.com");
		entity.setGopayServerTime("20111202115229");
		entity.setMerchantID("123123");
		entity.setTranAmt("100");
		entity.setTranCode("8888");
		entity.setTranDateTime("20111202115229");
		entity.setTranIP("127.0.0.1");
		entity.setVirCardNoIn("111");
		entity.setMerOrderNum("111111111");
		GopaySignUtil.signPay(entity, "111111");
	}
}
