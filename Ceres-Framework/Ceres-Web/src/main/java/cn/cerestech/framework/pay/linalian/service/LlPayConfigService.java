package cn.cerestech.framework.pay.linalian.service;

import org.springframework.stereotype.Service;

import cn.cerestech.framework.pay.linalian.eums.LlPayConfigKey;
import cn.cerestech.framework.support.configuration.service.ConfigService;

@Service
public class LlPayConfigService extends ConfigService{
	
	/**
	 * 连连支付商户号
	 */
	public String mchid(){
		return query(LlPayConfigKey.LLPAY_MCH_ID).stringValue();
	}
	/**
	 * 连连支付md5 支付key
	 * @return
	 */
	public String md5PayKey(){
		return query(LlPayConfigKey.LLPAY_MD5_PAYKEY).stringValue();
	}
	
	/**
	 * 连连支付 RSA 私钥
	 */
	public String privateRsaKey(){
		return query(LlPayConfigKey.LLPAY_RSA_PRIVATE_PAYKEY).stringValue();
	}
	
	/**
	 * 连连支付RSA 公钥
	 */
	public String publicRsaKey(){
		return query(LlPayConfigKey.LLPAY_RSA_PUBLIC_PAYKEY).stringValue();
	}
	
	/**
	 * 连连支付官方公钥
	 */
	public String govRsaPublicKey(){
		return query(LlPayConfigKey.LLPAY_GOV_RSA_PUBLICKEY).stringValue();
	}
	
}
