package cn.cerestech.framework.pay.linalian.eums;

import cn.cerestech.framework.support.configuration.enums.ConfigKey;

public enum LlPayConfigKey implements ConfigKey{
	LLPAY_MCH_ID("LLPAY_MCH_ID","连连支付商户号","201507021000394502"),
	LLPAY_MD5_PAYKEY("LLPAY_MD5_PAYKEY","连连支付MD5支付key","Ceres2015LLpay1014CTD"),
	LLPAY_RSA_PRIVATE_PAYKEY("LLPAY_RSA_PRIVATE_PAYKEY", "连连支付RSA签名私钥key","MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANRpDW7JQ1xwgDxjsnEmxlNqDSK16tDy/v5ARr5Y+DuDUAN+dHr5LzLlS9KasBo2hu1ALSa+gUZ0OHOXdsik40h0B7v3ktUSvVBOHFQy6wN57+nAwz5tftxzeYnaCzZgDpxjK8Shn2c0SvcWM1gMKhhvY5fuVMAjU2pPAb3rA5hjAgMBAAECgYAhnPo+NOVPBJKWe+RqGYu6+YZYntco97s4eu13A9dMe6w20VUXfMVmVXjZPBdwHn7dnpFGl1EX2B5y1F48xDMfIweW4YHjCxvStdjmLuNUhncn5VOWZM9LauSKxAvZnxgaaXopJCPeylX4OkapgYoap8TLtNmgRrT5DnH50ZKpCQJBAP00HLT4dFmAUE1bupNmtKvPgCpcMVLD5VpzKQKmnCVIwXvC464xfMh9fDVMlIjKKHOmx8i6NRznBpghKmcrf+cCQQDWwZrW3LckpIFhjH2vktYtr+STuYcuyBd0Qj71yeookA5B6Z3HGKIqUkRk9vZCSsc7m/JX4JGuffhzfRFRIYQlAkBrtBAF9q1fKNJ/pWYerxBpCNGmsyKT5xoXOGcYZpCC14jdwQ+iGBDRI3eDIHkKGpvMXgQbYQGYsri+W1UzH3C/AkB2e1fu5NSR/cR3yifpfsx1Zk5ohfokADaYaJgNyLlMabXD/ZyTpG6LhNnBDlNs3Y6vv2jjvL0DFPLG3KB6L1CVAkEA6qn7bV04ffyvDMWGJrl0ra1lSVEAHfFlYPtpa+X51Z7tbwgAr0tCZ/0U5lczy7jtNXkEetUAUFPTxEiGrht3hg=="),
	LLPAY_RSA_PUBLIC_PAYKEY("LLPAY_RSA_PUBLIC_PAYKEY", "连连支付RSA验证公钥key","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDUaQ1uyUNccIA8Y7JxJsZTag0iterQ8v7+QEa+WPg7g1ADfnR6+S8y5UvSmrAaNobtQC0mvoFGdDhzl3bIpONIdAe795LVEr1QThxUMusDee/pwMM+bX7cc3mJ2gs2YA6cYyvEoZ9nNEr3FjNYDCoYb2OX7lTAI1NqTwG96wOYYwIDAQAB"),
	LLPAY_GOV_RSA_PUBLICKEY("LLPAY_GOV_RSA_PUBLICKEY","连连支付官方公钥","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB");
	
	
	private String key, desc,defaultValue;
	private LlPayConfigKey(String key, String desc, String defaultValue){
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
	
	public static LlPayConfigKey keyOf(String key){
		for (LlPayConfigKey item : LlPayConfigKey.values()) {
			if(item.key().equalsIgnoreCase(key)){
				return item;
			}
		}
		return null;
	}
}
