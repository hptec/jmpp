package cn.cerestech.middleware.sms;

import java.util.Date;
import java.util.function.Function;

import cn.cerestech.middleware.sms.entity.SmsRecord;

public interface SmsBatch {

	/**
	 * 设置计划时间
	 * @param planTimeSupplier
	 * @return
	 */
	SmsBatch setPlanTimeSupplier(Function<SmsRecord, Date> planTimeSupplier);
	
	
	void doSend();
}
