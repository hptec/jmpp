package cn.cerestech.framework.pay.linalian.bean;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import cn.cerestech.framework.core.Dates;
import cn.cerestech.framework.core.json.Jsons;

public class WebPayEntity {
	//版本号 	
	private String version = "1.0";// 	是 	变(6) 	版本号:1.0 
	//参数字符编码集
	private String charset_name = "UTF-8";// 	否 	变(18) 	商户网站使用的编码格式,支持 UTF-8(默 认)、GBK、GB2312、GB18030 
	//支付交易商户编号 
	private String oid_partner;// 	是 	定(18) 	商户编号是商户在连连支付支付平台上开 设的商户号码,为 18 位数字,如: 201304121000001004,此商户号为支付 交易时的商户号 
	//平台来源标示 	
	private String platform;// 	否 	变(32) 	该参数可实现多个商户号之间用户数据共 享,该标识填写主商户号即可。 
	//商户用户唯一编 号 	
	private String user_id;// 	是 	变(32) 	该用户在商户系统中的唯一编号,要求是该 编号在商户系统中唯一标识该用户 
	//时间戳 	
	private String timestamp;// 	是 	定(14) 	格 式 : YYYYMMDDH24MISS 14 位 数字,精确到秒,签名时间戳有效期 30 分钟 (包含服务器之间存在的误差)
	//
	private String sign_type = "MD5";// 	是 	定(3) RSA 或者 MD5
	private String sign;// 	是 	变(255)
	//-业务参数-------------------------------------------
	//
	private String busi_partner;// 	是 	定(6) 虚拟商品销售:101001 实物商品销售:109001 外部账户充值:108001
	private String no_order;// 	是 	变(32) 商户系统唯一订单号
	private String dt_order;// 	是 	定(14) 格 式 : YYYYMMDDH24MISS 字,精确到秒
	private String name_goods;// 	是 	变(40) 
	private String info_order;// 	否 	变(255) 
	private String money_order;// 	是 	变(12) 该笔订单的资金总额,单位为 RMB-元。 大于 0 的数字,精确到小数点后两位。 如:49.65
	private String notify_url;// 	是 	变(128) 
	private String url_return;// 	否 	变(128) 
	private String userreq_ip;// 	否 	变(32) 用户访问商户的请求 IP,银通支付网关会根 据这个 ip 校验用户支付的 ip 是否一致,防 止钓鱼 请将 IP 中的“.”替换为“_”,例如:IP 是 122.11.37.211 的,请转换为 122_11_37_211
	private String url_order;// 	否 	变(255) 合作系统中订单的详情链接地址
	private String valid_order;// 	否 	变(8) 分钟为单位,默认为 10080 分钟(7 天), 从创建时间开始,过了此订单有效时间此笔 订单就会被设置为失败状态不能再重新进 行支付。
	private String bank_code;// 	否 	定(8) 8 位数字具体对应的银行编号见附录,如果 此字段不为空,配合 pay_type 使用,则会 直接跳转到相应的银行快捷支付页面。此功 能提供给商户的用户直接跳转到指定的银 行快捷支付页面,更方便用户进行支付。
	private String pay_type;// 		否 	定(1) 2:快捷支付(借记卡) 3:快捷支付(信用卡) 此支付方式与指定银行网银编号配合使用
	private String no_agree;// 	否 	定(16) 已经记录快捷银行卡的用户,商户在调用的 时候可以与 pay_type 一块配合使用直接
	private String shareing_data;// 	否 	变(1024) 分帐商户号 1^分账业务类型 1^分帐金额1^分账说明 1|分帐商户号 2^分账业务类 型 2^分帐金额 2^分账说明 2|分帐商户号 3^分账业务类型 3^分帐金额 3^分账说明 3注:1、 分账商户号:为注册登记在连连银通系统的商户编号(18 位数字) 2、 分账业务类型:可与主收款业务类型busi_partner 一致 3、 分帐金额:元为单位,保留两位数字 4、 分账说明:不能超过 100 个汉字,不能有^和|符号分账方只支持除主收款方外的 3 个分账方
	
	//"风险控制参数(商户上线时, 风控部门会审核 该参数;误传或 漏传可能导致交 易误拦截,影响 正常交易) "	
	private String risk_item;// 	是 	无上限 	"此字段填写风控参数,采用 json 串的模式 传入,字段名和字段内容彼此对应好,例如: {"user_info_mercht_userno":"13958069 593","user_info_dt_register":"2013103 0122130","frms_ware_category ":"1002"} 相关内容见风控参数列表 "
	//证件类型 	
	private String id_type;// 	否 	变(2) 	非必须,默认为 0 0:身份证或企业经营证件,1: 户口簿,2: 护照,3.军官证,4.士兵证,5. 港澳居民来往 内地通行证,6. 台湾同胞来往内地通行 证,7. 临时身份证,8. 外国人居留证,9. 警 官证, X.其他证件,10.组织结构代码 
	//证件号码 	
	private String id_no;// 	否 	变(32) 	非必须 
	//银行账号姓名 	
	private String acct_name;// 	否 	变(12) 	非必须 
	//修改标记 	
	private String flag_modify;// 	否 	定(1) 	"非必须
	//银行卡号
	private String card_no;//否  银行卡号前置,卡号可以在商户的页面输入 
	//
	private String back_url;//否 银行卡号前置,需要修改卡号时,用户点击 返回的 url 链接地址
	
	public WebPayEntity(String oid_partner,String user_id, String busi_partner,String no_order,  String dt_order
			, String name_goods, String money_order, String notify_url, String url_return, RiskItem risk){
		this.oid_partner = oid_partner;
		this.user_id = user_id;
		this.busi_partner = busi_partner;
		this.no_order = no_order;
		this.dt_order = dt_order;
		this.name_goods = name_goods;
		this.money_order = money_order;
		this.notify_url = notify_url;
		this.url_return = url_return;
		this.timestamp = Dates.format(new Date(), "yyyyMMddHHmmss");
		this.risk_item = Jsons.toJson(risk, false, false);
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCharset_name() {
		return charset_name;
	}
	public void setCharset_name(String charset_name) {
		this.charset_name = charset_name;
	}
	public String getOid_partner() {
		return oid_partner;
	}
	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getBusi_partner() {
		return busi_partner;
	}
	public void setBusi_partner(String busi_partner) {
		this.busi_partner = busi_partner;
	}
	public String getNo_order() {
		return no_order;
	}
	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}
	public String getDt_order() {
		return dt_order;
	}
	public void setDt_order(String dt_order) {
		this.dt_order = dt_order;
	}
	public String getName_goods() {
		return name_goods;
	}
	public void setName_goods(String name_goods) {
		this.name_goods = name_goods;
	}
	public String getInfo_order() {
		return info_order;
	}
	public void setInfo_order(String info_order) {
		this.info_order = info_order;
	}
	public String getMoney_order() {
		return money_order;
	}
	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getUrl_return() {
		return url_return;
	}
	public void setUrl_return(String url_return) {
		this.url_return = url_return;
	}
	public String getUserreq_ip() {
		return userreq_ip;
	}
	public void setUserreq_ip(String userreq_ip) {
		this.userreq_ip = userreq_ip;
	}
	public String getUrl_order() {
		return url_order;
	}
	public void setUrl_order(String url_order) {
		this.url_order = url_order;
	}
	public String getValid_order() {
		return valid_order;
	}
	public void setValid_order(String valid_order) {
		this.valid_order = valid_order;
	}
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getNo_agree() {
		return no_agree;
	}
	public void setNo_agree(String no_agree) {
		this.no_agree = no_agree;
	}
	public String getShareing_data() {
		return shareing_data;
	}
	public void setShareing_data(String shareing_data) {
		this.shareing_data = shareing_data;
	}
	public String getRisk_item() {
		return risk_item;
	}
	public void setRisk_item(String risk_item) {
		this.risk_item = risk_item;
	}
	public String getId_type() {
		return id_type;
	}
	public void setId_type(String id_type) {
		this.id_type = id_type;
	}
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	public String getAcct_name() {
		return acct_name;
	}
	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}
	public String getFlag_modify() {
		return flag_modify;
	}
	public void setFlag_modify(String flag_modify) {
		this.flag_modify = flag_modify;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getBack_url() {
		return back_url;
	}
	public void setBack_url(String back_url) {
		this.back_url = back_url;
	}
	public boolean validate(){
		boolean bol = StringUtils.isNotBlank(version)
				&& StringUtils.isNotBlank(oid_partner)
				&& StringUtils.isNotBlank(user_id)
				&& StringUtils.isNotBlank(timestamp)
				&& StringUtils.isNotBlank(sign_type)
				&& StringUtils.isNotBlank(busi_partner)
				&& StringUtils.isNotBlank(no_order)
				&& StringUtils.isNotBlank(dt_order)
				&& StringUtils.isNotBlank(name_goods)
				&& StringUtils.isNotBlank(money_order)
				&& StringUtils.isNotBlank(notify_url)
				&& StringUtils.isNotBlank(risk_item);
		return bol;
	}
}