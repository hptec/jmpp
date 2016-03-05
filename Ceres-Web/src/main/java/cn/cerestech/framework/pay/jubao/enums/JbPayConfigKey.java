package cn.cerestech.framework.pay.jubao.enums;

import cn.cerestech.framework.support.configuration.enums.ConfigKey;

public enum JbPayConfigKey  implements ConfigKey{
	JB_CFG_FILE("JB_CFG_FILE", "聚宝支付配置文件", ""),
	JB_CERT_DIR("JB_CERT_DIR", "聚宝支付证书文件目录，末尾不加斜杠", ""),
	JB_MCH_NO("JB_MCH_NO","聚宝支付商户号","");
	
	private String key, desc, defaultValue;
	private JbPayConfigKey(String key, String desc, String defaultValue){
		this.key = key;
		this.desc = desc;
		this.defaultValue = defaultValue;
	}
	@Override
	public String key() {
		return this.key;
	}
	@Override
	public String defaultValue() {
		return this.defaultValue;
	}
	@Override
	public String desc() {
		return this.desc;
	}
	
	public static JbPayConfigKey keyOf(String key){
		for (JbPayConfigKey item : JbPayConfigKey.values()) {
			if(item.key().equalsIgnoreCase(key)){
				return item;
			}
		}
		return null;
	}
	
}