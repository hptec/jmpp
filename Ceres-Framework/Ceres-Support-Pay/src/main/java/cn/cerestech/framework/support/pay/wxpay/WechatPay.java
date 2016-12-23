package cn.cerestech.framework.support.pay.wxpay;

import java.util.Date;
import java.util.Map;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.pay.wxpay.bean.UnifiedOrderEntity;
import cn.cerestech.framework.support.pay.wxpay.bean.UnifiedResult;
import cn.cerestech.framework.support.pay.wxpay.enums.TradeType;
import cn.cerestech.framework.support.pay.wxpay.publics.CommonUtil;
import cn.cerestech.framework.support.pay.wxpay.publics.SignUtil;
import cn.cerestech.framework.support.pay.wxpay.publics.UnifiedOrderUtil;

import com.google.common.collect.Maps;

/**
 * 公众号支付
 *
 */
public class WechatPay {
	
	public static Result<Object> pay(UnifiedOrderEntity entity, String payKey){
		entity.setTrade_type(TradeType.JSAPI.key());
		
		Result<Object> res = UnifiedOrderUtil.pay(entity, payKey);
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
			return  Result.success().setObject(map);
		}else{
			return res;
		}
	}
}
