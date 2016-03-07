package cn.cerestech.middleware.sms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.middleware.sms.enums.SmsState;

@Table(name = "$$sms_record")
public class SmsRecord extends IdEntity {

	public String provider;

	public String state;
	@Column(length = 25)
	public String ip;
	public String phone;
	@Column(length = 500)
	public String content;

	public Date send_time;

	public Date recive_time;

	public String outer_id;

	public String outer_code;

	@Type(type = "text")
	public String message;

	public String business_type;

	public Date plan_time;

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

	public String getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}

	public String getOuter_id() {
		return outer_id;
	}

	public void setOuter_id(String outer_id) {
		this.outer_id = outer_id;
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

	public Date getPlan_time() {
		return plan_time;
	}

	public void setPlan_time(Date plan_time) {
		this.plan_time = plan_time;
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

	public Date getSend_time() {
		return send_time;
	}

	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}

	public Date getRecive_time() {
		return recive_time;
	}

	public void setRecive_time(Date recive_time) {
		this.recive_time = recive_time;
	}

	public String getOuter_code() {
		return outer_code;
	}

	public void setOuter_code(String outer_code) {
		this.outer_code = outer_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
