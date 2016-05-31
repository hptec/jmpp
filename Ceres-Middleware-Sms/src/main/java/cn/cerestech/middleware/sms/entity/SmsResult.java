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
public class SmsResult {

	private Boolean isSuccess;
	private String outerTransId;// 外部交易号
	private String outerCode;// 外部响应代码
	private String message;// 相应内容
	@Type(type = "text")
	private String retJson;// 响应内容
	private String remark;// 备注

	public Boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getOuterTransId() {
		return outerTransId;
	}

	public void setOuterTransId(String outerTransId) {
		this.outerTransId = outerTransId;
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

	public String getRetJson() {
		return retJson;
	}

	public void setRetJson(String retJson) {
		this.retJson = retJson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
