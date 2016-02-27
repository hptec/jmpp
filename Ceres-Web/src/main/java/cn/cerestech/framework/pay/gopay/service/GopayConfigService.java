package cn.cerestech.framework.pay.gopay.service;

import org.springframework.stereotype.Service;

import cn.cerestech.framework.pay.gopay.enums.GopayConfigKey;
import cn.cerestech.framework.support.configuration.service.ConfigService;

@Service
public class GopayConfigService extends ConfigService{
	
	public String mchid(){
		return query(GopayConfigKey.GOPAY_MERCHANT_ID).stringValue();
	}
	
	public String vercode(){
		return query(GopayConfigKey.GOPAY_MERCHANT_VERCODE).stringValue();
	}
	
	public String tljacc(){
		return query(GopayConfigKey.GOPAY_MERCHANT_TLJ_ACC).stringValue();
	}
}
