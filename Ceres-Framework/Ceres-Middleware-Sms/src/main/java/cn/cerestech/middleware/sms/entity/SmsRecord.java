package cn.cerestech.middleware.sms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.middleware.sms.converter.SmsStateConverter;
import cn.cerestech.middleware.sms.enums.SmsState;

@Entity
@Table(name = "$$sms_record")
public class SmsRecord extends IdEntity {

	// 发送通道
	private String provider;

	// 发送状态
	@Convert(converter = SmsStateConverter.class)
	@Column(length = 15)
	private SmsState state;
	@Column(length = 25)
	private String clientIp;
	private String reciver;
	@Column(length = 500)
	private String content;

	// 发送时间
	private Date sendedTime;

	// 接收时间
	private Date recivedTime;

	private String outerId;

	private String outerCode;

	private String message;

	// 业务类型，即引发此短信的业务节点
	private String invokerType;

	// 计划发送时间
	private Date planTime;

<<<<<<< .mine
=======
	@Type(type = "text")
	@Lob
>>>>>>> .r206
	private String remark;

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public SmsState getState() {
		return state;
	}

	public void setState(SmsState state) {
		this.state = state;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public String getOuterCode() {
		return outerCode;
	}

	public void setOuterCode(String outerCode) {
		this.outerCode = outerCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInvokerType() {
		return invokerType;
	}

	public void setInvokerType(String invokerType) {
		this.invokerType = invokerType;
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
