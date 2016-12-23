package cn.cerestech.framework.support.pay.wxpay.publics;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import cn.cerestech.framework.core.utils.Encrypts;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

public class SignUtil {
	/**
	 * 微信支付签名算法
	 * @param params：参与签名的参数
	 * @param payKey: 商户支付秘钥
	 * @return
	 */
	public static String sign(Map<String, String> params, String payKey){
		Set<String> sets = Sets.newHashSet();
		params.forEach((k,v)->{
			if(!Strings.isNullOrEmpty(v)){
				sets.add(k+"="+v);
			}
		});
		String[] arr = new String[sets.size()];//sets.toArray();
		sets.toArray(arr);
		
		Arrays.sort(arr);
		StringBuffer sb = new StringBuffer();
		Arrays.asList(arr).forEach(itm->{
			sb.append(itm).append("&");
		});
		sb.append("key=").append(payKey);
		return Encrypts.md5(sb.toString()).toUpperCase();
	}
	
	public static String sign(Map<String, String> params){
		Set<String> sets = Sets.newHashSet();
		params.forEach((k,v)->{
			if(!Strings.isNullOrEmpty(v)){
				sets.add(k+"="+v);
			}
		});
		String[] arr = new String[sets.size()];//sets.toArray();
		sets.toArray(arr);
		
		Arrays.sort(arr);
		StringBuffer sb = new StringBuffer();
		Arrays.asList(arr).forEach(itm->{
			sb.append(itm).append("&");
		});
		
		return Encrypts.md5(sb.substring(0, sb.length()-1).toString()).toUpperCase();
	}
}
