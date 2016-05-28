package cn.cerestech.middleware.sms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.middleware.location.ip.IP;
import cn.cerestech.middleware.location.mobile.Mobile;
import cn.cerestech.middleware.sms.converter.SmsProviderConverter;
import cn.cerestech.middleware.sms.converter.SmsStateConverter;
import cn.cerestech.middleware.sms.enums.SmsProvider;
import cn.cerestech.middleware.sms.enums.SmsState;

@Entity
@Table(name = "$$sms_record")
public class SmsRecord extends IdEntity {

	// 发送通道
	@Convert(converter = SmsProviderConverter.class)
	@Enumerated(EnumType.STRING)
	private SmsProvider provider;

	// 发送状态
	@Convert(converter = SmsStateConverter.class)
	@Column(length = 15)
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

	// 接收时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date recivedTime;

	@Embedded
	private SmsSendResult result;

	@Type(type = "text")
	private String remark;

	public SmsState getState() {
		return state;
	}

	public void setState(SmsState state) {
		this.state = state;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public SmsProvider getProvider() {
		return provider;
	}

	public void setProvider(SmsProvider provider) {
		this.provider = provider;
	}

	public IP getIp() {
		return ip;
	}

	public void setIp(IP ip) {
		this.ip = ip;
	}

	public Mobile getTo() {
		return to;
	}

	public void setTo(Mobile to) {
		this.to = to;
	}

	public SmsSendResult getResult() {
		return result;
	}

	public void setResult(SmsSendResult result) {
		this.result = result;
	}

	public Date getSendedTime() {
		return sendedTime;
	}

	public void setSendedTime(Date sendedTime) {
		this.sendedTime = sendedTime;
	}

	public Date getRecivedTime() {
		return recivedTime;
	}

	public void setRecivedTime(Date recivedTime) {
		this.recivedTime = recivedTime;
	}

	public Date getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
