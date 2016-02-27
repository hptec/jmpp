package cn.cerestech.framework.web.service;

import org.springframework.stereotype.Service;

import cn.cerestech.framework.support.configuration.service.ConfigService;
import cn.cerestech.framework.web.enums.WebConfigKey;

@Service
public class WebConfigService extends ConfigService {
	/**
	 * 当前系统的主机域名url 包括http 或者 https 协议前缀
	 * 
	 * @return
	 */
	public String host() {
		return query(WebConfigKey.SITE_HOST).stringValue();
	}
	/**
	 * 外部资源路径
	 * @return
	 */
	public String outResPath() {
		return query(WebConfigKey.OUT_RES_PATH).stringValue();
	}
	
	/**
	 * 站点关键字
	 * @return
	 */
	public String keywords(){
		return query(WebConfigKey.SITE_KEYWORD).stringValue();
	}
	/**
	 * 网站描述
	 * @return
	 */
	public String desc(){
		return query(WebConfigKey.SITE_DESC).stringValue();
	}
	/**
	 * 站点名称
	 * @return
	 */
	public String name(){
		return query(WebConfigKey.SITE_NAME).stringValue();
	}
	/**
	 * 站点简称
	 * @return
	 */
	public String shortName(){
		return query(WebConfigKey.SITE_SHORT_NAME).stringValue();
	}
	
}
