package cn.cerestech.framework.support.pay.wxpay.bean;

/**
 * 通知给微信服务器我收到了支付通知
 *
 */
public class PayNotifyBack {
	private String return_code;
	private String return_msg;
	
	public PayNotifyBack(){}
	public PayNotifyBack(String return_code, String return_msg){
		this.return_code = return_code;
		this.return_msg = return_msg;
	}
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
}
