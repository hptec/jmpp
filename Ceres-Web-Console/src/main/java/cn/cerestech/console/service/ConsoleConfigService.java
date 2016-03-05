package cn.cerestech.console.service;

import org.springframework.stereotype.Service;

import cn.cerestech.console.enums.ConsoleConfigKey;
import cn.cerestech.framework.support.configuration.service.ConfigService;

@Service
public class ConsoleConfigService extends ConfigService {

	/**
	 * 是否开启后台管理员的行为日志记录
	 * 
	 * @return
	 */
	public Boolean isEmployeeActionLogEnable() {
		return query(ConsoleConfigKey.EMPLOYEE_ACTION_LOG_ENABLE).boolValue();
	}
}
