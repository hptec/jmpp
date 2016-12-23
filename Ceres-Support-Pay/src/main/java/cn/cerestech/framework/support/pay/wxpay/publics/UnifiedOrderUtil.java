package cn.cerestech.framework.support.pay.wxpay.publics;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.logging.log4j.util.Strings;

import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.core.xml.Xmls;
import cn.cerestech.framework.support.pay.wxpay.bean.UnifiedOrderEntity;
import cn.cerestech.framework.support.pay.wxpay.bean.UnifiedResult;

import com.google.common.collect.Maps;

/**
 * 统一下单工具接口
 * 
 */
public class UnifiedOrderUtil {
	private static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	public static Result pay(UnifiedOrderEntity entity, String payKey){
		Map<String, String> signMap = Maps.newHashMap();
		
		Arrays.asList(UnifiedOrderEntity.class.getDeclaredFields()).forEach(filed->{
			PropertyDescriptor des;
			try {
				des = new PropertyDescriptor(filed.getName(), UnifiedOrderEntity.class);
				Method get = des.getReadMethod();
				Object valobj = get.invoke(entity);
				String val = valobj != null ?valobj.toString():"";
				if(!Strings.isNotBlank(val) && !filed.getName().equals("sign")){
					signMap.put(filed.getName(), val);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		String sign = SignUtil.sign(signMap, payKey);
		entity.setSign(sign);//设置签名
		
		String postStrEntity = Xmls.objectToXml(entity, "xml");
		
		if(entity.validate()){
			UnifiedResult res = null;
			int times = 3;
			do{
				//统一下单接口
				String retxml = Https.of().post(UNIFIED_ORDER_URL, new ByteArrayEntity(postStrEntity.getBytes())).readString();
				res = Xmls.xmlToObject(UnifiedResult.class, retxml, true);
				--times;
			}while(times > 0 && (res == null || Strings.isBlank(res.getResult_code())));
			
			return Result.success().setObject(res);
		}else{
			return Result.error().setMessage("参数提交失败！");
		}
	}
}
