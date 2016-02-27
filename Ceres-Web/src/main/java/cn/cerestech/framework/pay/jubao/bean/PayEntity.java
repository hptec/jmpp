package cn.cerestech.framework.pay.jubao.bean;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class PayEntity {
	private String payid;//				是 订单号,由商户生成 
	private String partnerid;// 		是 	Varchar(32) 	商户号 
	private String amount;// 			是 	Varchar(16) 	订单金额元,精确到小数点后 2 位 
	private String payerName;// 		是 	Varcahr(100) 	客户 ID
	private String ip;// 				是 	Varchar(60) 	客户的 IP 
	private String goodsName;// 		是 	Varchar(100) 	商品名称 
	private String payMethod="ALL";// 	是 	Varchar(100) 	支付方式。见附录 
	private String remark;// 			是 	Varchar(100) 	 
	private String returnURL;// 	 	否 	Varchar(100) 	页面回调 URL(如果后台没有设置,则使用该 URL, 否则使用后台配置的 URL) 
	private String callBackURL;// 	  	否	Varchar(100) 	后台回调 URL(如果后台没有设置,则使用该 URL, 否则使用后台配置的 URL)
	
	public String getPayid() {
		return payid;
	}
	public void setPayid(String payid) {
		this.payid = payid;
	}
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReturnURL() {
		return returnURL;
	}
	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}
	public String getCallBackURL() {
		return callBackURL;
	}
	public void setCallBackURL(String callBackURL) {
		this.callBackURL = callBackURL;
	}
	
	public boolean validate(){
		if(StringUtils.isBlank(this.getAmount()) 
				|| StringUtils.isBlank(this.getGoodsName())
				|| StringUtils.isBlank(this.getIp())
				|| StringUtils.isBlank(this.getPartnerid())
				|| StringUtils.isBlank(this.getPayerName())
				|| StringUtils.isBlank(this.getPayid())
				|| StringUtils.isBlank(this.getPayMethod())
				|| StringUtils.isBlank(this.getRemark())
				|| !NumberUtils.isNumber(this.getAmount())){
			return false;
		}
		return true;
	}
}
