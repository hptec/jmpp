package cn.cerestech.middleware.sms.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.configuration.service.ConfigService;
import cn.cerestech.middleware.sms.dao.SmsDao;
import cn.cerestech.middleware.sms.entity.SmsRecord;
import cn.cerestech.middleware.sms.enums.SmsConfigKeys;
import cn.cerestech.middleware.sms.enums.SmsState;
import cn.cerestech.middleware.sms.providers.HuoniProvider;
import cn.cerestech.middleware.sms.providers.SmsProvider;
import cn.cerestech.middleware.sms.providers.YunPianProvider;

@Service
public class SmsMessageService {

	private static Set<SmsProvider> providers = Sets.newHashSet(new YunPianProvider(), new HuoniProvider());

	@Autowired
	ConfigService configService;

	@Autowired
	SmsDao smsDao;

	public void beginGroup() {

	}

	/**
	 * 是否开启短信发送功能
	 * 
	 * @return
	 */
	public Boolean isSMSEnabled() {
		return configService.query(SmsConfigKeys.SMS_ENABLE).boolValue();
	}

	public Set<SmsProvider> getProviders() {
		return providers;
	}

	public SmsProvider getProvider(String name) {
		return getProviders().stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst()
				.orElse(defaultProvider());
	}

	/**
	 * 设置服务供应商，如果找不到，则返回空
	 * 
	 * @param name
	 * @return
	 */
	public SmsProvider setProvider(String name) {
		SmsProvider smsProvider = getProviders().stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst()
				.orElse(null);
		if (smsProvider == null) {
			return null;
		}
		configService.update(SmsConfigKeys.SMS_DEFAULT_PROVIDER, name);
		return smsProvider;
	}

	public SmsProvider defaultProvider() {
		String name = configService.query(SmsConfigKeys.SMS_DEFAULT_PROVIDER).stringValue();
		for (SmsProvider p : getProviders()) {
			if (p.getName().equalsIgnoreCase(name)) {
				return p;
			}
		}

		return getProviders().stream().findFirst().orElse(new YunPianProvider());
	}

	public void defaultProvider(SmsProvider provider) {
		configService.update(SmsConfigKeys.SMS_DEFAULT_PROVIDER, provider.getName());
	}

	protected void sendInstant(String phone, String text) {

	}

	public Result<SmsRecord> send(SmsRecord sr, Boolean checkFrequency) {
		return sendSms(sr, checkFrequency);
	}

	public Result<SmsRecord> send(SmsRecord sr) {
		return send(sr, Boolean.TRUE);
	}

	private Result<SmsRecord> sendSms(SmsRecord sr, Boolean checkFrequency) {
		return null;
	}

	public Result<SmsRecord> send(String phone, Date planTime, String content, String business_type,
			boolean checkFrequency) {
		return null;
	}

	public Result<SmsRecord> send(String phone, Date planTime, String templateId, boolean checkFrequency,
			String... args) {
		// 解析模板
		return null;
	}

	public Result<SmsRecord> send(String phone, String templateId, boolean checkFrequency, String... args) {
		return send(phone, null, templateId, checkFrequency, args);
	}

	public Result<SmsRecord> send(String phone, String templateId, String... args) {
		return send(phone, null, templateId, true, args);
	}

	public Result<SmsRecord> send(String phone, Date plannedTime, String templateId, String... args) {
		return send(phone, plannedTime, templateId, true, args);
	}

	private SmsRecord persist(SmsRecord sr) {
		smsDao.save(sr);
		return sr;
	}

	public int ipSendSmsCount(String ip, long offsetNow) {
		return 1;
	}

	public int phoneSmsCount(String phone, long offsetNow) {
		return 1;
	}

	public List<? extends SmsRecord> search(Class<? extends SmsRecord> cls, SmsProvider smsProvider, SmsState state,
			String phone, Date fromDate, Date toDate, Integer maxRecords) {

		StringBuffer where = new StringBuffer(" 1=1 ");
		if (smsProvider != null) {
			where.append(" AND provider='" + smsProvider.getName() + "'");
		}

		if (state != null) {
			where.append(" AND `state` = '" + state.key() + "'");
		}
		if (!Strings.isNullOrEmpty(phone) && !Strings.nullToEmpty(phone).contains("'")) {
			where.append(" AND phone LIKE '%" + phone + "%'");
		}

		if (fromDate != null) {
		}

		if (toDate != null) {
		}
		// 排序
		where.append(" ORDER BY create_time DESC ");

		// 组装分页
		where.append(" LIMIT " + maxRecords);

		return null;
	}
}
