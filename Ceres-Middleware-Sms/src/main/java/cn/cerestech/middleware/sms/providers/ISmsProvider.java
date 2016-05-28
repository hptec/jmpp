package cn.cerestech.middleware.sms.providers;

import cn.cerestech.framework.core.parser.Parser;
import cn.cerestech.middleware.sms.enums.SmsProviderAuthKey;

public interface ISmsProvider {

	public SmsProviderAuthKey[] authKeys();

	public Parser parser();

	public ISmsSender sender();

}
