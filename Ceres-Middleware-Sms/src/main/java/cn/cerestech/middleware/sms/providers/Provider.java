package cn.cerestech.middleware.sms.providers;

import java.util.Map;

import cn.cerestech.middleware.location.mobile.Mobile;
import cn.cerestech.middleware.sms.SmsFireWall;
import cn.cerestech.middleware.sms.entity.SmsResult;
import cn.cerestech.middleware.sms.enums.SmsProvider;
import cn.cerestech.middleware.sms.enums.SmsProviderAuthKey;

public interface Provider {

	public SmsProviderAuthKey[] authKeys();

	public SmsResult send(Mobile to, String content, Map<String, Object> params);

	/**
	 * 默认为云片
	 * 
	 * @return
	 */
	default SmsProvider getSmsProvider() {
		return SmsProvider.YUNPIAN;
	}

	default SmsFireWall getFireWall() {
		return null;
	}

}
