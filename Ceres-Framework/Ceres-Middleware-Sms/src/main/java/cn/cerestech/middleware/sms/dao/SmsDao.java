package cn.cerestech.middleware.sms.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.middleware.sms.entity.SmsRecord;
import cn.cerestech.middleware.sms.enums.SmsState;

public interface SmsDao extends JpaRepository<SmsRecord, Long> {
	// List<SmsRecord>
	// findByPlatformIdAndIpAndStateAndSendTimeNotNullAndSendTimeGreaterThan(Long
	// platformId, String ip,
	// String state, Date sentTime);

	/**
	 * 指定ip下指定时间段内是否有发送成功的短信
	 * 
	 * @param state
	 * @param ip
	 * @param date
	 * @return
	 */
	SmsRecord findUniqueBySendedTimeIsNotNullAndSendedTimeGreaterThanAndStateAndIpAddr(Date date, SmsState state,
			String ip);

	/**
	 * 指定ip下指定时间段内是否有发送成功的短信
	 * 
	 * @param state
	 * @param ip
	 * @param date
	 * @return
	 */
	SmsRecord findUniqueBySendedTimeIsNotNullAndSendedTimeGreaterThanAndStateAndToNumber(Date date, SmsState state,
			String to);
}
