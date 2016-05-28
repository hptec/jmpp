package cn.cerestech.middleware.sms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.middleware.location.ip.IP;
import cn.cerestech.middleware.location.mobile.Mobile;
import cn.cerestech.middleware.sms.enums.SmsProvider;
import cn.cerestech.middleware.sms.enums.SmsState;

@Entity
@Table(name = "$$sms_record")
public class SmsRecord extends IdEntity {

	// 发送通道
	@Enumerated(EnumType.STRING)
	private SmsProvider provider;

	// 发送状态
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

	@Embedded
	private SmsSendResult result;

	@Type(type = "text")
	private String remark;

	@ManyToOne
	@JoinColumn(name = "batch_id")
	private SmsBatch batch;

	/**
	 * 开发者可以用两种方式设置短信的内容<br/>
	 * 1. setContent(String) 直接设置<br/>
	 * 2. setContent(String,Object) 使用模板解析,结息规则取决于SmsProvider中提供的Parser逻辑
	 */
	@Transient
	private Object contentParameter;
	@Transient
	private String contentTemplate;

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

	public SmsRecord setContent(String template, Object parameter) {
		this.contentTemplate = template;
		this.contentParameter = parameter;
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

	public SmsSendResult getResult() {
		return result;
	}

	public SmsRecord setResult(SmsSendResult result) {
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

	public SmsBatch getBatch() {
		return batch;
	}

	public SmsRecord setBatch(SmsBatch batch) {
		this.batch = batch;
		return this;
	}

	public Date getPlanTime() {
		return planTime;
	}

	public SmsRecord setPlanTime(Date planTime) {
		this.planTime = planTime;
		return this;
	}

	public String getRemark() {
		return remark;
	}

	public SmsRecord setRemark(String remark) {
		this.remark = remark;
		return this;
	}

	public Object getContentParameter() {
		return contentParameter;
	}

	public String getContentTemplate() {
		return contentTemplate;
	}

	/**
	 * 使用默认的短信服务提供商
	 * 
	 * @return
	 */
	public static SmsRecord fromDefault() {
		SmsRecord sms = new SmsRecord();
		sms.setProvider(SmsProvider.YUNPIAN);
		return sms;
	}

}
