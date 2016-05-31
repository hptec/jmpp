package cn.cerestech.middleware.sms.providers;

import java.util.Date;

import cn.cerestech.middleware.location.ip.IP;
import cn.cerestech.middleware.location.mobile.Mobile;

/**
 * 短信
 * 
 * @author harryhe
 *
 */
public interface Sms {

	Mobile getTo();

	String getContent();

	IP getIp();

	default Date getPlanTime() {
		return null;
	}

}
