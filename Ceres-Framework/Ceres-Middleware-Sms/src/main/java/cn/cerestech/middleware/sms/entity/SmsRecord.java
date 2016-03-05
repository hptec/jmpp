package cn.cerestech.middleware.sms.entity;

import java.util.Date;

import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.framework.persistence.enums.ColumnDataType;
import cn.cerestech.middleware.sms.enums.SmsState;

@SuppressWarnings("serial")
@Table("$$sms_record")
public class SmsRecord extends BaseEntity {

	@Column(title = "供应商")
	public String provider;

	@Column(title = "状态")
	public String state;
	@Column(title = "客户IP", length = 25)
	public String ip;
	@Column(title = "收件人")
	public String phone;
	@Column(title = "内容", length = 500)
	public String content;

	@Column(title = "发送时间")
	public Date send_time;

	@Column(title = "接收时间")
	public Date recive_time;

	@Column(title = "外部ID", length = 75)
	public String outer_id;

	@Column(title = "外部响应代码")
	public String outer_code;

	@Column(type = ColumnDataType.TEXT, title = "错误消息")
	public String message;
	
	@Column(title = "消息分类，商业类型")
	public String business_type;

	@Column(title = "计划发送时间")
	public Date plan_time;

	@Column(title = "备注", length = 200)
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
