package cn.cerestech.framework.pay.jubao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.pay.jubao.enums.JbPayConfigKey;
import cn.cerestech.framework.support.configuration.service.ConfigService;
import cn.cerestech.framework.web.service.WebConfigService;

@Service
public class JbPayConfigService extends ConfigService{
	@Autowired
	WebConfigService webConfig;
	
	public String cfgFile(){
		return query(JbPayConfigKey.JB_CFG_FILE).stringValue();
	}
	public String certDir(){
		return query(JbPayConfigKey.JB_CERT_DIR).stringValue();
	}
	public String mchno(){
		return query(JbPayConfigKey.JB_MCH_NO).stringValue();
	}
	
	public String host(){
		return webConfig.host();
	}
}
