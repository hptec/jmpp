package cn.cerestech.middleware.sms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.collect.Lists;

import cn.cerestech.middleware.location.mobile.Mobile;
import cn.cerestech.middleware.sms.providers.SmsProvider;

public class SmsBatch {
	private List<SmsRecord> records = Lists.newArrayList();

	@Temporal(TemporalType.TIMESTAMP)
	private Date plantime;

	private SmsProvider provider;

	private String templateKey;

	public SmsBatch(SmsProvider provider) {
		this.provider = provider;
	}

	public SmsBatch() {
	}

	public SmsRecord createSms(Mobile to) {
		SmsRecord sms = new SmsRecord();
		sms.setTo(to);
		records.add(sms);
		return sms;
	}

	public SmsBatch plan(Date plantime) {
		this.plantime = plantime;
		return this;
	}

	public static SmsBatch from(SmsProvider provider) {
		SmsBatch batch = new SmsBatch(provider);

		return batch;
	}

	public SmsProvider getProvider() {
		return provider;
	}

}
