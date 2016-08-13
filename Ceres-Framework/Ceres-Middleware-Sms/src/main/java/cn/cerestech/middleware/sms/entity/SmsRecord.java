package cn.cerestech.middleware.sms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.cerestech.framework.support.persistence.entity.IdEntity;
import cn.cerestech.middleware.location.ip.IP;
import cn.cerestech.middleware.location.mobile.Mobile;
import cn.cerestech.middleware.sms.enums.SmsProvider;
import cn.cerestech.middleware.sms.enums.SmsState;
import cn.cerestech.middleware.sms.providers.Sms;

@Entity
@Table(name = "$$sms_record")
public class SmsRecord extends IdEntity implements Sms {

	// 发送通道
	@Enumerated(EnumType.STRING)
	private SmsProvider provider;

	// 发送状态
	@Column(length = 15)
	@Enumerated(EnumType.STRING)
	private SmsState state;

	@Embedded
	private IP ip;
	@Embedded
	private Mobile to;

	@Column(length = 500)
	private String content;

	// 计划发送时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date planTime;

	// 发送时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendedTime;

	@Embedded
	private SmsResult result;

	public static SmsRecord from(Sms sms) {
		SmsRecord record = new SmsRecord();

		record.setContent(sms.getContent());
		record.setIp(sms.getIp());
		record.setPlanTime(sms.getPlanTime());
		record.setTo(sms.getTo());
		return record;
	}

	public SmsState state() {
		return state;
	}

	public SmsRecord setState(SmsState state) {
		this.state = state;
		return this;
	}

	public String getContent() {
		return content;
	}

	public SmsRecord setContent(String content) {
		this.content = content;
		return this;
	}

	public SmsProvider getProvider() {
		return provider;
	}

	public SmsRecord setProvider(SmsProvider provider) {
		this.provider = provider;
		return this;
	}

	public IP getIp() {
		return ip;
	}

	public SmsRecord setIp(IP ip) {
		this.ip = ip;
		return this;
	}

	public Mobile getTo() {
		return to;
	}

	public SmsRecord setTo(Mobile to) {
		this.to = to;
		return this;
	}

	public SmsResult getResult() {
		return result;
	}

	public SmsRecord setResult(SmsResult result) {
		this.result = result;
		return this;
	}

	public Date getSendedTime() {
		return sendedTime;
	}

	public SmsRecord setSendedTime(Date sendedTime) {
		this.sendedTime = sendedTime;
		return this;
	}

	public Date getPlanTime() {
		return planTime;
	}

	public SmsRecord setPlanTime(Date planTime) {
		this.planTime = planTime;
		return this;
	}

}
