package cn.cerestech.framework.pay.linalian.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cn.cerestech.framework.core.Encrypts;

public class SignUtils {
	
	public static <T> String signMd5(T entity, String payMd5Key){
		String signStr = signStr(entity);
		signStr += "&key="+payMd5Key;
		return md5(signStr);
	}
	
	public static <T> String signRSA(T entity, String privateKey){
		String signStr = signStr(entity);
		return rsa(signStr, privateKey);
	}
	
	private static String md5(String signStr){
		return Encrypts.md5(signStr);
	}
	
	public static String rsa(String signStr, String privateKey){
		try
        {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.getBytesBASE64(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
            // 用私钥对信息生成数字签名
            java.security.Signature signet = java.security.Signature
                    .getInstance("MD5withRSA");
            signet.initSign(myprikey);
            signet.update(signStr.getBytes("UTF-8"));
            byte[] signed = signet.sign(); // 对信息的数字签名
            return new String(org.apache.commons.codec.binary.Base64.encodeBase64(signed));
        }catch (java.lang.Exception e){
            e.printStackTrace();
        }
        return null;
	}
	
	public static <T> String signStr(T entity){
		Map<String, String> params = Maps.newHashMap();
		Arrays.asList(entity.getClass().getDeclaredFields()).forEach(filed->{
			PropertyDescriptor des;
			try {
				des = new PropertyDescriptor(filed.getName(), entity.getClass());
				Method get = des.getReadMethod();
				Object valobj = get.invoke(entity);
				String val = valobj != null ?valobj.toString():"";
				if(StringUtils.isNotBlank(val) && !filed.getName().equals("sign")){
					params.put(filed.getName(), val);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return signStr(params);
	}
	
	public static String signStr(Map<String, String> params){
		StringBuffer signStr = new StringBuffer();
		Set<String> sets = Sets.newHashSet();
		params.forEach((k, v)->{
			if(!"sign".equals(k) && StringUtils.isNotBlank(v)){
				sets.add(k+"="+v);
			}
		});
		String[] arr = new String[sets.size()];
		sets.toArray(arr);
		Arrays.sort(arr);
		Arrays.asList(arr).forEach(itm->{
			signStr.append(itm).append("&");
		});
		if(signStr.length()>0 && signStr.lastIndexOf("&") == signStr.length() -1){
			signStr.deleteCharAt(signStr.length() -1);
		}
		
		return signStr.toString();
	}
	
	/**
	 * 校验签名
	 * @param params
	 * @param payMd5Key
	 * @param payRsaKey
	 * @return
	 */
	public static boolean checkSign(Map<String, String> params, String payMd5Key, String payRsaGovPubKey){
		String sign_type = params.get("sign_type");
		if("MD5".equalsIgnoreCase(sign_type)){
			return checkMd5Sign(params, payMd5Key);
		}else{
			return checkRsaSign(params, payRsaGovPubKey);
		}
	}
	
	public static boolean checkMd5Sign(Map<String, String> params, String payMd5Kdy){
		System.out.println("进入商户[" + params.get("oid_partner") + "]MD5签名验证");
		if(params == null || params.isEmpty() || StringUtils.isBlank(payMd5Kdy)){
			return false;
		}
		String sign = params.get("sign");
		// 生成待签名串
		String sign_src = signStr(params);
		sign_src += "&key="+payMd5Kdy;
		String sign_des = md5(sign_src);
		return Strings.nullToEmpty(sign_des).equals(sign);
	}
	/**
	 * 
	 * @param params
	 * @param payRsaKey 公钥
	 * @return
	 */
	public static boolean checkRsaSign(Map<String, String> params, String payRsaGovPubKey){
		System.out.println("进入商户[" + params.get("oid_partner") + "]RSA签名验证");
		if(params == null || params.isEmpty() || StringUtils.isBlank(payRsaGovPubKey)){
			return false;
		}
		 String sign = params.get("sign");
		 // 生成待签名串
		 String sign_src = signStr(params);
		 System.out.println("商户[" + params.get("oid_partner") + "]待签名原串" + sign_src);
		 System.out.println("商户[" + params.get("oid_partner") + "]签名串" + sign);
		 try
		 {
			 try{
		            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(
		                    Base64.getBytesBASE64(payRsaGovPubKey));
		            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		            PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
		            byte[] signed = Base64.getBytesBASE64(sign);// 这是SignatureData输出的数字签
		            java.security.Signature signetcheck = java.security.Signature
		                    .getInstance("MD5withRSA");
		            signetcheck.initVerify(pubKey);
		            signetcheck.update(sign_src.getBytes("UTF-8"));
		            return signetcheck.verify(signed);
		        } catch (java.lang.Exception e){
		            e.printStackTrace();
		        }
		        return false;
		 } catch (Exception e){
		     System.out.println("商户[" + params.get("oid_partner") + "]RSA签名验证异常" + e.getMessage());
		     return false;
		 }
	}
	
}
