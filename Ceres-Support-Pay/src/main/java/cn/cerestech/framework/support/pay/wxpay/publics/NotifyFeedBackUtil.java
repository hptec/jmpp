package cn.cerestech.framework.support.pay.wxpay.publics;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cerestech.framework.core.http.HttpIO;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.xml.Xmls;
import cn.cerestech.framework.support.pay.wxpay.bean.PayNotify;
import cn.cerestech.framework.support.pay.wxpay.bean.PayNotifyBack;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

public class NotifyFeedBackUtil {
	/**
	 * 通知返回
	 * @param request
	 * @return
	 */
	public static PayNotify notifyBack(HttpServletRequest request){
		String res  = HttpIO.in(request);
		return notifyBack(res);
	}

	public static PayNotify notifyBack(String xml){
		try{
			PayNotify result = Xmls.xmlToObject(PayNotify.class, xml, true);
			return result;
		}catch(Exception e){
		}
		return null;
	}
	/**
	 * 通知微信服务器收到了通知
	 */
	public static void recivedFeedbackNotify(String msg, HttpServletResponse response){
		PayNotifyBack  back = new PayNotifyBack("SUCCESS", Strings.nullToEmpty(msg));
		String ret = Xmls.objectToXml(back, "xml");
		HttpIO.out(response, ret , "UTF-8");
	}
	
	
	/**
	 * 校验支付通知是否合法 签名
	 * @param notify
	 * @param payKey
	 * @return
	 */
	public static boolean isLegal(PayNotify notify, String payKey){
		Field[] fields = PayNotify.class.getDeclaredFields();
		Map<String, String> params = Maps.newHashMap();
		Arrays.asList(fields).forEach(field->{
			String name = field.getName();
			PropertyDescriptor des;
			try {
				des = new PropertyDescriptor(field.getName(), PayNotify.class);
				Method get = des.getReadMethod();
				Object valobj = get.invoke(notify);
				String val = valobj != null ?(String)valobj:"";
				if(!Strings.isNullOrEmpty(val)){
					params.put(field.getName(), val);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return isLegal(params, payKey);
	}
	/**
	 * 判断支付通知是否合法
	 * @param params
	 * @param payKey
	 * @return
	 */
	public static boolean isLegal(Map<String, String> params, String payKey){
		String sign = params.get("sign");
		if(Strings.isNullOrEmpty(sign)){
			return false;
		}
		params.remove("sign");
		
		Map<String, String> signParam = Maps.newHashMap();
		for (String key : params.keySet()) {
			if(!"sign".equals(key)){
				signParam.put(key, params.get(key));
			}
		}
		String signSys = SignUtil.sign(signParam, payKey); 
		return sign.equals(signSys);
	}
	
	public static void main(String[] args) {
		String xml = Xmls.objectToXml(new PayNotifyBack("SUCCESS" , "OK"), "xml");
		
		Map<String, Object> map = Xmls.xmlToMap(xml, true);
		
		System.out.println(Jsons.from(map).toJson());
	}
}
