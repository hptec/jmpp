package cn.cerestech.framework.web;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.Props;

public class Web {
	public static final String FILENAME = "web.properties";

	public static String loginFrontUrl() {
		return Props.on(FILENAME).v("login_front_url").stringValue();
	}
	public static String loginWapUrl(){
		return Props.on(FILENAME).v("login_wap_url").stringValue();
	}
	
	public static String imgRoot(){
		return Props.on(FILENAME).v("img_root").stringValue();
	}
	
	/**
	 * WEB中Cookie参数的默认值
	 * 
	 * @author harryhe
	 *
	 */
	public static class Cookie {
		public static String path() {
			String v = Props.on(FILENAME).v("cookie_path").stringValue();
			if (Strings.isNullOrEmpty(v)) {
				v = "/";
			}
			return v;
		}

		public static Integer maxAge() {
			Integer v = Props.on(FILENAME).v("cookie_maxage").intValue();
			return v;
		}
	}
}
