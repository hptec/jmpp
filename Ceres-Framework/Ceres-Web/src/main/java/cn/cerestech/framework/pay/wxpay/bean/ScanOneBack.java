package cn.cerestech.framework.pay.wxpay.bean;

import javax.servlet.http.HttpServletRequest;

public class ScanOneBack {
	// 公众账号ID
	private String appid;// String(32) 是 wx8888888888888888 微信分配的公众账号ID
	// 用户标识
	private String openid;// String(128) 是 o8GeHuLAsgefS_80exEr1cTqekUs
							// 用户在商户appid下的唯一标识
	// 商户号
	private String mch_id;// String(32) 是 1900000109 微信支付分配的商户号

	// 是否关注公众账号
	private String is_subscribe;// String(1) 是 Y
								// 用户是否关注公众账号，仅在公众账号类型支付有效，取值范围：Y或N;Y-关注;N-未关注
	// 随机字符串
	private String nonce_str;// String(32) 是 5K8264ILTKCH16CQ2502SI8ZNMTM67VS
								// 随机字符串，不长于32位。推荐随机数生成算法
	// 商品ID
	private String product_id;// String(32) 是 88888 商户定义的商品id 或者订单号
	// 签名
	private String sign;// String(32) 是 C380BEC2BFD727A4B6845133519F3AD6
						// 返回数据签名，签名生成算法

	public static ScanOneBack instance(HttpServletRequest request) {
		ScanOneBack one = new ScanOneBack();
		one.setAppid(request.getParameter("appid"));
		one.setOpenid(request.getParameter("openid"));
		one.setMch_id(request.getParameter("mch_id"));
		one.setIs_subscribe(request.getParameter("is_subscribe"));
		one.setNonce_str(request.getParameter("nonce_str"));
		one.setProduct_id(request.getParameter("product_id"));
		one.setSign(request.getParameter("sign"));
		return one;
		
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getIs_subscribe() {
		return is_subscribe;
	}

	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
