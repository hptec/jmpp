package cn.cerestech.framework.pay.gopay.bean;

import org.apache.commons.lang3.StringUtils;

public class PayEntity {
	public String version = "2.1";//必须
	public String charset = "2"; //非  格式:数字 1 GBK 2 UTF-8 默认为 1 本域指明了商户端的编码格式,网 关会依据此对中文域进行解析,响 应信息按 GBK 编码方式返回
	public String language = "1"; //必须  1 中文 2 英文
	public String signType = "1"; //非 1 MD5 2 SHA
	public String tranCode = "8888";// 必须  格式:数字 本域指明了交易的类型,支付网关 接口必须为 8888
	public String merchantID;//必须  签约国付宝商户唯一用户 ID
	public String merOrderNum; //必须 用于传送商户订单号信息,每笔新的 交易需生成一笔新的订单号,如果 isRepeatSubmit 为 1,则未支付的订 单号可以重复提交,但要保证交易金 额一致
	public String tranAmt;//必须 本域值仅包含交易本金,不包含任 何服务费,且其值在交易的整个过  以元为单位
	public String feeAmt;//非  本域值为提留手续费金额,且不能 超过交易金额域 tranAmt
	public String currencyType = "156";// 必须  多币种预留字段,暂只能为 156,代 表人民币
	public String frontMerUrl;//与后台 通知地址不能同时为空 商户前台地址。表明商户希望交易结 果回复的前台页面显示地址。与后台 通知地址不能同时为空。通知规则见
	public String backgroundMerUrl;//商户后台地址。表明商户希望交易结 果回复的后台响应接受地址。与前台 通知地址不能同时为空。通知规则见
	public String tranDateTime;//必须  YYYYMMDDHHMMSS 本域为订单发起的交易时间
	public String virCardNoIn;//必须  本域指卖家在国付宝平台开设的国付宝账户号。
	public String tranIP;//必须  发起交易的客户 IP 地址。
	public String isRepeatSubmit ="1";//非 0 不允许重复 1 允许重复 默认
	public String goodsName;//非  参与交易的商品名称,该字段不能含有特殊字符
	public String goodsDetail;//非 参与交易商品的详细信息,该字段不能含有特殊字符
	public String buyerName;//非 格式:字母
	public String buyerContact; //非 参与交易的买方联系方式
	public String merRemark1;//非 扩展字段
	public String merRemark2;//非 扩展字段
	public String signValue;//必须  将交易信息用 signType 域设置的方 式加密后生成的字符串
	public String gopayServerTime;//从国付宝服务器获取当前时间后,在规定时间内发送的订单交易,才视为成功交易,如果支付接口收到此值超过国付宝服务器当前时间,拒绝此次交易请求。开启时间戳防钓鱼机制必填
	public String bankCode;// 直连银行时必须使用的参数,用来判 断连接哪家银行。当此域与 userType 同时上送时, 系统将采取直连银行模式接入
	public String userType = "1";//判断客户是个人支付还是企业支付。 当此域与 bankCode 同时上送时, 系统将采取直连银行模式接入 1(为个人支付); 2(为企业支付); 3(信用卡快捷支付); 4(借记卡快捷支付)。
	public String remark;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getMerchantID() {
		return merchantID;
	}
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}
	public String getMerOrderNum() {
		return merOrderNum;
	}
	public void setMerOrderNum(String merOrderNum) {
		this.merOrderNum = merOrderNum;
	}
	public String getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}
	public String getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getFrontMerUrl() {
		return frontMerUrl;
	}
	public void setFrontMerUrl(String frontMerUrl) {
		this.frontMerUrl = frontMerUrl;
	}
	public String getBackgroundMerUrl() {
		return backgroundMerUrl;
	}
	public void setBackgroundMerUrl(String backgroundMerUrl) {
		this.backgroundMerUrl = backgroundMerUrl;
	}
	public String getTranDateTime() {
		return tranDateTime;
	}
	public void setTranDateTime(String tranDateTime) {
		this.tranDateTime = tranDateTime;
	}
	public String getVirCardNoIn() {
		return virCardNoIn;
	}
	public void setVirCardNoIn(String virCardNoIn) {
		this.virCardNoIn = virCardNoIn;
	}
	public String getTranIP() {
		return tranIP;
	}
	public void setTranIP(String tranIP) {
		this.tranIP = tranIP;
	}
	public String getIsRepeatSubmit() {
		return isRepeatSubmit;
	}
	public void setIsRepeatSubmit(String isRepeatSubmit) {
		this.isRepeatSubmit = isRepeatSubmit;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsDetail() {
		return goodsDetail;
	}
	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerContact() {
		return buyerContact;
	}
	public void setBuyerContact(String buyerContact) {
		this.buyerContact = buyerContact;
	}
	public String getMerRemark1() {
		return merRemark1;
	}
	public void setMerRemark1(String merRemark1) {
		this.merRemark1 = merRemark1;
	}
	public String getMerRemark2() {
		return merRemark2;
	}
	public void setMerRemark2(String merRemark2) {
		this.merRemark2 = merRemark2;
	}
	public String getSignValue() {
		return signValue;
	}
	public void setSignValue(String signValue) {
		this.signValue = signValue;
	}
	public String getGopayServerTime() {
		return gopayServerTime;
	}
	public void setGopayServerTime(String gopayServerTime) {
		this.gopayServerTime = gopayServerTime;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public boolean validate(){
		boolean bol = StringUtils.isNotBlank(version)
				&& StringUtils.isNotBlank(language)
				&& StringUtils.isNotBlank(tranAmt)
				&& StringUtils.isNotBlank(merchantID)
				&& StringUtils.isNotBlank(merOrderNum)
				&& StringUtils.isNotBlank(tranAmt)
				&& StringUtils.isNotBlank(currencyType)
				&& StringUtils.isNotBlank(tranDateTime)
				&& StringUtils.isNotBlank(virCardNoIn)
				&& StringUtils.isNotBlank(tranIP);
		
		return bol;
	}
}
