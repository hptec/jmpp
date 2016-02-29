package cn.cerestech.framework.pay.linalian.wap;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.pay.linalian.bean.WapPayEntity;
import cn.cerestech.framework.pay.linalian.utils.SignUtils;

public class LLWapPay {
	
	public static final String gate_way = "https://yintong.com.cn/llpayh5/payment.htm";
	
	public static Result payForm(WapPayEntity entity, String payMd5Key, String payRsaKey){
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
			form.append("<input name=\"req_data\" value='"+Jsons.toJson(entity, false, false)+"'>");
			form.append("</form>");
			return Result.success().setObject(form.toString());
		}else{
			return Result.error().setMessage("缺少参数");
		}
	}
	
}
