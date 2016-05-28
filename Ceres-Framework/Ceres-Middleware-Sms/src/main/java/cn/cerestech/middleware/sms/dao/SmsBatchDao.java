package cn.cerestech.middleware.sms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.middleware.sms.entity.SmsBatch;

public interface SmsBatchDao extends JpaRepository<SmsBatch, Long> {
	// List<SmsRecord> reses = mysqlService.queryBy(SmsRecord.class,
	// "ip='" + Strings.nullToEmpty(ip) + "' AND `state`='" +
	// SmsState.SUCCESS.key()
	// + "' AND send_time IS NOT NULL AND send_time > '" +
	// FORMAT_DATETIME.format(offset) + "'");
	// List<SmsRecord>
	// findByPlatformIdAndIpAndStateAndSendTimeNotNullAndSendTimeGreaterThan(Long
	// platformId, String ip,
	// String state, Date sentTime);
}
