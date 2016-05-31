package cn.cerestech.middleware.sms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.middleware.location.mobile.Mobile;
import cn.cerestech.middleware.sms.enums.SmsProvider;

@Entity
@Table(name = "$$sms_batch")
public class SmsBatch1 extends IdEntity {

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "batch")
	private List<SmsRecord> records;

	@Temporal(TemporalType.TIMESTAMP)
	private Date planTime;

	private SmsProvider provider;

	private String template;

	public SmsBatch1(SmsProvider provider) {
		this.provider = provider;
	}

	public SmsBatch1() {
	}

	public SmsRecord createSms(Mobile to) {
		SmsRecord sms = new SmsRecord();
		sms.setTo(to);
		sms.setBatch(this);
		records.add(sms);
		return sms;
	}

	public SmsBatch1 plan(Date plantime) {
		this.planTime = plantime;
		return this;
	}

	public Date getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}

	public static SmsBatch1 from(SmsProvider provider) {
		SmsBatch1 batch = new SmsBatch1(provider);

		return batch;
	}

	public SmsProvider getProvider() {
		return provider;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public void setProvider(SmsProvider provider) {
		this.provider = provider;
	}

	public List<SmsRecord> getRecords() {
		return records;
	}

}
