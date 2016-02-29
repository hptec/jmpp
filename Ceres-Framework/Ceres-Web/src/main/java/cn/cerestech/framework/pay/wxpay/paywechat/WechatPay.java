package cn.cerestech.framework.pay.wxpay.paywechat;

import java.util.Date;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.pay.wxpay.bean.UnifiedOrderEntity;
import cn.cerestech.framework.pay.wxpay.bean.UnifiedResult;
import cn.cerestech.framework.pay.wxpay.enums.TradeType;
import cn.cerestech.framework.pay.wxpay.publics.CommonUtil;
import cn.cerestech.framework.pay.wxpay.publics.SignUtil;
import cn.cerestech.framework.pay.wxpay.publics.UnifiedOrderUtil;

/**
 * 公众号支付
 * @author bird
 *
 */
public class WechatPay {
	
	public static Result pay(UnifiedOrderEntity entity, String payKey){
		entity.setTrade_type(TradeType.JSAPI.key());
		
		Result res = UnifiedOrderUtil.pay(entity, payKey);
		if(res.isSuccess()){
			UnifiedResult unifiedResult = (UnifiedResult)res.getObject();
			if("SUCCESS".equals(unifiedResult.getReturn_code()) && "SUCCESS".equals(unifiedResult.getResult_code())){
				
			}else if("FAIL".equals(unifiedResult.getReturn_code())){
				return Result.error().setMessage(unifiedResult.getReturn_msg());
			}else{
				return Result.error().setMessage(unifiedResult.getErr_code_des());
			}
			
			Map<String,String> map = Maps.newHashMap();
			map.put("appId", entity.getAppid());
			map.put("timeStamp",Long.toString(new Date().getTime() / 1000));
			map.put("nonceStr", CommonUtil.noncestr(16));
			map.put("package", "prepay_id="+unifiedResult.getPrepay_id());
			map.put("signType", "MD5");
			map.put("paySign", SignUtil.sign(map,payKey));
			System.out.println(map.get("paySign"));
			return  Result.success().setObject(map);
		}else{
			return res;
		}
	}
}
