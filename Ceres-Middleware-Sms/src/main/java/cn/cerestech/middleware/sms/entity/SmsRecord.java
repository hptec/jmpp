package cn.cerestech.middleware.sms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.middleware.sms.enums.SmsState;

@Entity
@Table(name = "$$sms_record")
public class SmsRecord extends IdEntity {

	public String provider;

	public String state;
	@Column(length = 25)
	public String ip;
	public String phone;
	@Column(length = 500)
	public String content;

	public Date sendTime;

	public Date reciveTime;

	public String outerId;

	public String outerCode;

	@Type(type = "text")
	public String message;

	public String businessType;

	public Date planTime;

	@Column(length = 200)
	public String remark;

	public Boolean stateEqual(SmsState state) {
		EnumCollector stateEnum = EnumCollector.forClass(SmsState.class);
		if (this.state == null) {
			return Boolean.FALSE;
		} else {
			return stateEnum.keyOf(this.state).equals(state);
		}
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getReciveTime() {
		return reciveTime;
	}

	public void setReciveTime(Date reciveTime) {
		this.reciveTime = reciveTime;
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

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Date getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}

}
