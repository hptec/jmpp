package cn.cerestech.middleware.sms.enums;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.Func;
import cn.cerestech.framework.core.Props;

public class Sms {

	public static final String FILENAME = "sms.properties";


	public static String template(String tplKey, String... args) {
		String tmpl = Props.on(FILENAME).getProperty(tplKey);
		if(Strings.isNullOrEmpty(tmpl)){
			return null;
		}
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
