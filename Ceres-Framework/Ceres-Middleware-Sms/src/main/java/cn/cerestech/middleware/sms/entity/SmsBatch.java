package cn.cerestech.middleware.sms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.middleware.location.mobile.Mobile;
import cn.cerestech.middleware.sms.converter.SmsProviderConverter;
import cn.cerestech.middleware.sms.enums.SmsProvider;
import cn.cerestech.middleware.sms.providers.ISmsProvider;

@Entity
@Table(name = "$$sms_batch")
public class SmsBatch extends IdEntity {

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "batch_id", referencedColumnName = "id")
	private List<SmsRecord> records;

	@Temporal(TemporalType.TIMESTAMP)
	private Date plantime;

	@Convert(converter = SmsProviderConverter.class)
	private ISmsProvider provider;

	private String template;

	public SmsBatch(ISmsProvider provider) {
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

	public ISmsProvider getProvider() {
		return provider;
	}

	public Date getPlantime() {
		return plantime;
	}

	public void setPlantime(Date plantime) {
		this.plantime = plantime;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public void setProvider(ISmsProvider provider) {
		this.provider = provider;
	}

}
