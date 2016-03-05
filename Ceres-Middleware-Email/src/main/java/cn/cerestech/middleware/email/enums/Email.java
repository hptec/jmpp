package cn.cerestech.middleware.email.enums;

import cn.cerestech.framework.core.Func;
import cn.cerestech.framework.core.Props;

public class Email {
	public static final String FILENAME = "email.properties";


	public static String template(String tplKey, String... args) {
		String tmpl = Props.on(FILENAME).getProperty(tplKey);
		String content = Func.replacablePlain(tmpl, args);
		return content;
	}

	public static Long sameIpInterval() {
		return Props.on(FILENAME).v("sms.send.sameip.interval").longValue();
	}

	public static Long samePhoneInterval() {
		return Props.on(FILENAME).v("sms.send.samephone.interval").longValue();
	}
}
