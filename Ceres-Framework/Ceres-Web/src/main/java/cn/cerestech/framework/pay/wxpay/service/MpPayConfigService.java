package cn.cerestech.framework.pay.wxpay.service;

import org.springframework.stereotype.Service;

import cn.cerestech.framework.pay.wxpay.enums.MpPayConfigKey;
import cn.cerestech.framework.support.configuration.service.ConfigService;

@Service
public class MpPayConfigService extends ConfigService{
//	@Autowired
//	MpConfigService mpConfig;
//	
//	public String appid(){
//		return mpConfig.appid();
//	}
//	
//	public String appsecret(){
//		return mpConfig.appsecret();
//	}
	
	public String mchno(){
		return query(MpPayConfigKey.WECHAT_PAY_MCHNO).stringValue();
	}
	
	public String payKey(){
		return query(MpPayConfigKey.WECHAT_PAY_PAYKEY).stringValue();
	}
	
}
