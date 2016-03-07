package cn.cerestech.middleware.sms.monitor;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.core.monitor.AbstractMonitor;
import cn.cerestech.middleware.sms.entity.SmsRecord;
import cn.cerestech.middleware.sms.enums.SmsState;
import cn.cerestech.middleware.sms.service.SmsMessageService;

/**
 * 用于处理微信账户提现的后台程序
 * 
 * @author harryhe
 *
 */
public class SmsMonitor extends AbstractMonitor<SmsRecord> {

	@Autowired
	SmsMessageService smsMessageService;

	@Autowired
	MysqlService mysqlService;

	@Autowired
	ConfigService configService;

	@Override
	public Function<SmsRecord, Boolean> getMonitor() {
		return sms -> {
			if (sms.getState() == null) {
				// 立即发送(按理说不会遇到，以防万一);
				smsMessageService.send(sms);
				return Boolean.TRUE;
			} else if (sms.stateEqual(SmsState.PLANNING)) {
				// 计划发送
				if (sms.getPlan_time() == null) {
					// 立即发送
					smsMessageService.send(sms);
					return Boolean.TRUE;
				} else if (sms.getPlan_time().getTime() <= System.currentTimeMillis()) {
					// 时候已到
					smsMessageService.send(sms);
					return Boolean.TRUE;
				} else {
					// 时候未到
					return Boolean.FALSE;
				}
			} else {
				// 成功或者失败，从当前队列移除
				return Boolean.TRUE;
			}
		};
	}

	@Override
	public Supplier<List<SmsRecord>> getReloader() {
		return () -> {
			return mysqlService.queryBy(SmsRecord.class, " state = '" + SmsState.PLANNING.key() + "'");
		};
	}

	@Override
	public Boolean isRunable() {
		if (getLastRunTime() == null) {
			return Boolean.TRUE;
		} else {
			return System.currentTimeMillis() > (getLastRunTime().getTime() + 1 * 1000L);
		}
	}

}
