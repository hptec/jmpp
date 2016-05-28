package cn.cerestech.middleware.sms.entity;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

/**
 * 发送结果
 * 
 * @author harryhe
 *
 */
@Embeddable
public class SmsSendResult {

	private Boolean isSuccess;
	private String outer_id;// 发送事件ID
	private String outer_code;// 响应代码
	private String message;// 相应内容
	@Type(type = "text")
	private String remark;// 备注

	public Boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getOuter_id() {
		return outer_id;
	}

	public void setOuter_id(String outer_id) {
		this.outer_id = outer_id;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
