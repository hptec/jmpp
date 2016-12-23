package cn.cerestech.framework.support.pay.wxpay.service;


import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;


@Service
@ConfigurationProperties(prefix="mp")
public class WxPayConfig {
	
//	@NotNull
	private String mchid;
	
//	@NotNull
	private String paykey;
	public String getMchid() {
		return mchid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public String getPaykey() {
		return paykey;
	}
	public void setPaykey(String paykey) {
		this.paykey = paykey;
	}
	
}
