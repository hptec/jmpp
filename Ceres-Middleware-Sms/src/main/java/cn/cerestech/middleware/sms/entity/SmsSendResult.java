package cn.cerestech.middleware.sms.entity;

public class SmsSendResult {

	private Boolean isSuccess;
	private String outer_id;
	private String outer_code;
	private String message;
	private String remark;

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
