package cn.cerestech.framework.support.pay.wxpay.bean;

import java.util.Arrays;

import com.google.common.base.Strings;

public class UnifiedOrderEntity {
	private String appid;// 必须
	private String mch_id;// 必须
	private String device_info;//
	private String nonce_str;// 必须
	private String sign;// 必须
	private String body;// 必须
	private String detail;//
	private String attach;//商品明细列表，可以为空
	private String out_trade_no;// 必须
	private String fee_type;//支付单位  人民币
	private String total_fee;// 必须 整数
	private String spbill_create_ip;// 必须
	private String time_start;//
	private String time_expire;//
	private String goods_tag;//
	private String notify_url;// 必须
	private String trade_type;// 必须
	private String product_id;//
	private String openid;//
	
	public boolean validate(){
		boolean canGo = true;
		String errMsg = "";
		if(Strings.isNullOrEmpty(appid)){
			canGo = false;
			errMsg= "appid IS NULL";
		}
		
		if(Strings.isNullOrEmpty(mch_id)){
			canGo = false;
			errMsg= "mch_id IS NULL";
		}
		if(Strings.isNullOrEmpty(nonce_str)){
			canGo = false;
			errMsg= "nonce_str IS NULL";
		}
		if(Strings.isNullOrEmpty(sign)){
			canGo = false;
			errMsg= "sign IS NULL";
		}
		if(Strings.isNullOrEmpty(body)){
			canGo = false;
			errMsg= "body IS NULL";
		}
		if(Strings.isNullOrEmpty(out_trade_no)){
			canGo = false;
			errMsg= "out_trade_no IS NULL";
		}
		if(Integer.parseInt(total_fee) < 0){
			canGo = false;
			errMsg= "total_fee LT ZERO";
		}
		if(Strings.isNullOrEmpty(spbill_create_ip)){
			canGo = false;
			errMsg= "spbill_create_ip is NULL";
		}
		
		if(Strings.isNullOrEmpty(notify_url)){
			canGo = false;
			errMsg= "notify_url is NULL";
		}		
		if(Strings.isNullOrEmpty(trade_type)){
			canGo = false;
			errMsg= "trade_type is NULL";
		}
		
		if(!canGo){
			throw new RuntimeException(errMsg);
		}
		
		return canGo;
	}
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	public static void main(String[] args) {
		Arrays.asList(UnifiedResult.class.getDeclaredFields()).forEach(t->{
			System.out.println(t.getName());
		});
	}
}
